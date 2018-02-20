package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSportFoundException

class SportSpec extends org.specs2.mutable.Specification {

  "Sport" >> {

    "apply with id 1" >> {

      val sport = Sport(1)

      sport.name must beEqualTo("Fußball")

    }

    "apply with id 12345678912345678 NoSeasonFoundException" >> {

      var errorCatched = false

      try {
        val season = Sport(12345678912345678L)
      } catch {
        case e: NoSportFoundException =>
          errorCatched = true
      }

      errorCatched must beTrue

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
