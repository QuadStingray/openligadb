package com.quadstingray.openligadb

class SportSpec extends org.specs2.mutable.Specification {

  "Sport" >> {

    "apply with id 1" >> {

      val sport = Sport(1)

      sport.name must beEqualTo("Fußball")

    }

    "getLeagues" >> {

      val sport = Sport(1, "Was auch immer")

      sport.name must beEqualTo("Was auch immer")

      val leagues = sport.leagues

      leagues.size must beGreaterThanOrEqualTo(241)
    }

    "getSeasons" >> {

      val sport = Sport(1, "Was auch immer")

      sport.name must beEqualTo("Was auch immer")

      val seasons = sport.seasons

      seasons.size must beGreaterThanOrEqualTo(311)
    }

  }


}
