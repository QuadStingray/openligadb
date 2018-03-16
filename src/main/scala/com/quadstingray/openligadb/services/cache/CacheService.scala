package com.quadstingray.openligadb.services.cache

import java.security.MessageDigest

private[openligadb] trait CacheService {

  def generateKey(s: String): String = MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString

  def getCachedElement(key: String): String

  def putElement(key: String, value: String): Unit

  def clearCaches(): Unit

  def putCount: Long

  def getCount: Long

  def missCount: Long

}
