package com.quadstingray.openligadb.services.cache

import java.security.MessageDigest

import com.github.blemale.scaffeine
import com.github.blemale.scaffeine.Scaffeine
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration.Duration

private[openligadb] class CacheService {

  val conf: Config = ConfigFactory.load

  private def timeUnit: Duration = {
    val myTimeUnit = Duration.apply(conf.getInt("com.quadstingray.openligadb.caching.expire.time"), conf.getString("com.quadstingray.openligadb.caching.expire.unit"))
    myTimeUnit
  }

  val webCallsCache: scaffeine.Cache[String, String] = Scaffeine().recordStats()
    .expireAfterWrite(timeUnit)
    .maximumSize(conf.getInt("com.quadstingray.openligadb.caching.capacity"))
    .build[String, String]()



  def generateKey(s: String): String = MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString

}
