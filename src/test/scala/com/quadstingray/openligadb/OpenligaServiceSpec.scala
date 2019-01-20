package com.quadstingray.openligadb

class OpenligaServiceSpec extends org.specs2.mutable.Specification {

  "OpenligaService" >> {

    "getAllAvailableLeagues" >> {

      //#find-league
      val league = OpenligaDb.availableLeagues
      //#find-league

      league.size must beGreaterThan(200)

      val bl1 = league.filter(league => "bl1".equalsIgnoreCase(league.shortName))

      bl1.size must beEqualTo(1)
    }

    "getAllAvailableSeasons " >> {
      val seasons = OpenligaDb.availableSeasons

      seasons.size must beGreaterThan(200)

      val bl1 = seasons.filter(season => "bl1".equalsIgnoreCase(season.league.shortName) && 2016 == season.year)

      bl1.size must beEqualTo(1)
    }

    "getAllAvailableSports " >> {
      val sports = OpenligaDb.availableSports

      sports.size must beGreaterThan(10)

      val soccer = sports.head

      soccer.id must beEqualTo(1)

      soccer.name must beEqualTo("Fußball")
    }

  }


}
