package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.GuiceModule
import com.quadstingray.openligadb.services.cache.CacheService
import org.joda.time.DateTime


class CacheSpec extends org.specs2.mutable.Specification {


  val cache: CacheService = GuiceModule.injector.getInstance(classOf[CacheService])

  "CacheSpec" >> {

    "check that cache is used" >> {

      val startHitCount = cache.webCallsCache.stats().hitCount()

      val startMissCount = cache.webCallsCache.stats().missCount()

      cache.webCallsCache.invalidateAll()

      val game1 = MatchData(39738)

      game1.id must beEqualTo(39738)

      game1.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game1.isFinished must beTrue

      val game2 = MatchData(39738)

      game2.id must beEqualTo(39738)

      game2.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game2.isFinished must beTrue

      val game3 = MatchData(39736)

      game3.id must beEqualTo(39736)

      game3.isFinished must beTrue

      cache.webCallsCache.stats().missCount() must beGreaterThan(1l)

      cache.webCallsCache.stats().hitCount() must beGreaterThan(1l)

    }

  }


}
