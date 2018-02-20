package com.quadstingray.openligadb.services

import java.security.MessageDigest
import java.util.concurrent.TimeUnit

import com.typesafe.config.Config
import org.cache2k.{Cache, Cache2kBuilder}

private[openligadb] object CacheHelper {

  import com.typesafe.config.ConfigFactory

  val conf: Config = ConfigFactory.load

  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString
  }

  private def timeUnit: TimeUnit = {
    val myTimeUnit = TimeUnit.valueOf(conf.getString("com.quadstingray.openligadb.caching.expire.unit"))
    myTimeUnit
  }

  val webCallsCache: Cache[String, String] = new Cache2kBuilder[String, String]() {}
    .name("webCallsCache")
    .permitNullValues(false)
    .expireAfterWrite(conf.getInt("com.quadstingray.openligadb.caching.expire.time"), timeUnit)
    .entryCapacity(conf.getInt("com.quadstingray.openligadb.caching.capacity"))
    .build()

}
