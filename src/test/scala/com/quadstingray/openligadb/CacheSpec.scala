package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.CacheHelper
import org.cache2k.core.HeapCache
import org.joda.time.DateTime


class CacheSpec extends org.specs2.mutable.Specification {

  "CacheSpec" >> {

    "check that cache is used" >> {

      CacheHelper.webCallsCache.clear()

      val game1 = Match(39738)

      game1.id must beEqualTo(39738)

      game1.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game1.isFinished must beTrue

      val game2 = Match(39738)

      game2.id must beEqualTo(39738)

      game2.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game2.isFinished must beTrue

      val game3 = Match(39736)

      game3.id must beEqualTo(39736)

      game3.isFinished must beTrue

      val cacheInfo = CacheHelper.webCallsCache.asInstanceOf[HeapCache[String, String]].getInfo()

      cacheInfo.getGetCount must beGreaterThan(cacheInfo.getPutCount)

      cacheInfo.getPutCount must between(cacheInfo.getMissCount - 5, cacheInfo.getMissCount + 5)

    }

  }


}
