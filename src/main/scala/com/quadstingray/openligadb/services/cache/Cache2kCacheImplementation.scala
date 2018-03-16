package com.quadstingray.openligadb.services.cache

import java.security.MessageDigest
import java.util.concurrent.TimeUnit

import com.typesafe.config.Config
import org.cache2k.core.HeapCache
import org.cache2k.{Cache, Cache2kBuilder}

private[openligadb] class Cache2kCacheImplementation extends CacheService {

  import com.typesafe.config.ConfigFactory

  val conf: Config = ConfigFactory.load

  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString
  }

  private def timeUnit: TimeUnit = {
    val myTimeUnit = TimeUnit.valueOf(conf.getString("com.quadstingray.openligadb.caching.expire.unit"))
    myTimeUnit
  }

  private val webCallsCache: Cache[String, String] = new Cache2kBuilder[String, String]() {}
    .name("webCallsCache")
    .permitNullValues(false)
    .expireAfterWrite(conf.getInt("com.quadstingray.openligadb.caching.expire.time"), timeUnit)
    .entryCapacity(conf.getInt("com.quadstingray.openligadb.caching.capacity"))
    .build()

  webCallsCache.asInstanceOf[HeapCache[String, String]].getInfo()

  override def getCachedElement(key: String): String = webCallsCache.get(key)

  override def putElement(key: String, value: String): Unit = webCallsCache.put(key, value)

  override def clearCaches(): Unit = webCallsCache.clear()

  override def putCount: Long = webCallsCache.asInstanceOf[HeapCache[String, String]].getInfo().getPutCount

  override def getCount: Long = webCallsCache.asInstanceOf[HeapCache[String, String]].getInfo().getGetCount

  override def missCount: Long = webCallsCache.asInstanceOf[HeapCache[String, String]].getInfo().getMissCount
}
