package com.quadstingray.openligadb.services

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import com.quadstingray.openligadb.services.cache.CacheService
import com.typesafe.scalalogging.LazyLogging
import org.json4s.DefaultFormats
import org.json4s.Xml.toJson
import org.json4s.native.Serialization._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.xml.factory.XMLLoader
import scala.xml.{Elem, Node, SAXParser}

trait HttpService {
  private[openligadb] def soap[T](body: String)(implicit m: Manifest[T]): T

  private[openligadb] def get(callUrl: String): String
}

private[openligadb] class AkkaHttpService @Inject()(cache: CacheService) extends LazyLogging with HttpService {
  implicit val defaultFormats: DefaultFormats.type = DefaultFormats
  implicit val system: ActorSystem = ActorSystem(java.util.UUID.randomUUID.toString)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  private[openligadb] def get(callUrl: String): String = {
    val request = HttpRequest(uri = callUrl, method = HttpMethods.GET)
    getFromCacheOrExecuteRequest(request)
  }

  private def parseForObject[T](body: String, deepth: Int = 0)(implicit m: Manifest[T]): T = {
    try {
      val resultObject = read[Map[String, T]](body)
      resultObject.head._2
    } catch {
      case e: org.json4s.MappingException =>
        if (deepth < 10) {
          parseForObject[Map[String, T]](body, deepth + 1).head._2
        } else {
          throw e
        }
    }
  }

  private[openligadb] def soap[T](body: String)(implicit m: Manifest[T]): T = {

    object SecureXmlParser extends XMLLoader[Elem] {

      import javax.xml.parsers.SAXParserFactory

      override def parser: SAXParser = {
        val factory = SAXParserFactory.newInstance
        factory.setNamespaceAware(false)
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
        factory.newSAXParser
      }
    }

    def findResultNode(elem: Node): Node = {
      if (elem.label.toLowerCase().contains("result")) {
        elem
      } else {
        elem.child.foreach(node => {
          return findResultNode(node)
        })

        null
      }
    }

    val requestEnntity = HttpEntity(ContentTypes.`text/xml(UTF-8)`, body)
    val request = HttpRequest(uri = "https://www.openligadb.de/Webservices/Sportsdata.asmx", method = HttpMethods.POST, entity = requestEnntity)

    val parsedResult = findResultNode(SecureXmlParser.loadString(getFromCacheOrExecuteRequest(request)))

    val jsonString = write(toJson(parsedResult))

    parseForObject[T](jsonString)

  }

  private def getFromCacheOrExecuteRequest(request: HttpRequest): String = {
    val cachingString = "%s - %s - %s".format(request.method.value, request.uri, request.entity)
    val cacheKey = cache.generateKey(cachingString)
    val cacheResult = cache.getCachedElement(cacheKey)
    if (cacheResult == null) {
      val responseFuture: Future[HttpResponse] = requestWithRedirect(request)
      val response = Await.result(responseFuture, 60.seconds)
      val result = Await.result(Unmarshal(response.entity).to[String], 60.seconds).replaceAll(" xsi:nil=\"true\" />", " />")
      cache.putElement(cacheKey, result)
      result
    } else {
      cacheResult
    }

  }

  private val maxRedirCount = 5

  private def requestWithRedirect(req: HttpRequest, count: Int = 0)(implicit system: ActorSystem, mat: Materializer): Future[HttpResponse] = {

    Http().singleRequest(req).flatMap { resp =>
      resp.status match {
        case StatusCodes.Found | StatusCodes.MovedPermanently | StatusCodes.SeeOther | StatusCodes.TemporaryRedirect | StatusCodes.PermanentRedirect =>
          resp.header[headers.Location].map { loc =>
            val locUri = loc.uri
            val newReq = req.copy(uri = locUri)
            if (count < maxRedirCount)
              requestWithRedirect(newReq, count + 1)
            else {
              Http().singleRequest(newReq)
            }
          }.getOrElse(throw new RuntimeException(s"location not found on 302 for ${req.uri}"))
        case _ => Future(resp)
      }
    }
  }

  def stop(): Unit = {
    system.terminate()
    materializer.shutdown()
    Http().shutdownAllConnectionPools().flatMap { _ =>
      materializer.shutdown()
      system.terminate()
    }
    Await.result(system.terminate(), Duration.Inf)
  }

  sys.ShutdownHookThread {
    stop()
  }

}
