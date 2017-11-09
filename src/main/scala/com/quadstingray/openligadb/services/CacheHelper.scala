package com.quadstingray.openligadb.services

import java.security.MessageDigest
import java.util.concurrent.TimeUnit

import org.cache2k.{Cache, Cache2kBuilder}

private[openligadb] object CacheHelper {

  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString
  }

  val webCallsCache: Cache[String, String] = new Cache2kBuilder[String, String]() {}
    .name("webCallsCache")
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .permitNullValues(true)
    .entryCapacity(1000)
    .build()

}
