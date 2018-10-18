package com.quadstingray.openligadb

import org.joda.time.DateTime


class LeagueSpec extends org.specs2.mutable.Specification {

  "League" >> {

    "getCurrentMatches must return List of 9 elements for bl1" >> {

      val league = League("bl1")

      val matches = league.currentMatches

      matches.size must equalTo(9)
    }

    "getCurrentSeason " >> {

      val league = League("bl1")

      val season = league.currentSeason

      val currentYear = new DateTime().getYear

      season.year must beBetween(currentYear - 1, currentYear + 1)

      season.league must beEqualTo(league)

    }


    "getSeason " >> {

      val league = League("bl1")

      val season = league.season(2015)

      season.year must beEqualTo(2015)

      season.id must beEqualTo(848)

      season.league must beEqualTo(league)

    }

    "getSeasons " >> {

      val league = League("bl1")

      val seasons = league.seasons

      seasons.size must beGreaterThanOrEqualTo(16)

    }

    "getCurrentMatchGroup " >> {

      val league = League("bl1")

      val matchGroup = league.currentMatchGroup

      matchGroup.id must beGreaterThanOrEqualTo(28957l)

      matchGroup.matchGroupOrderNumber must beGreaterThanOrEqualTo(1)

      matchGroup.name must beEqualTo("%s. Spieltag".format(matchGroup.matchGroupOrderNumber))

    }

    "lastMatch " >> {

      val league = League("cl15")

      val openLigaMatch = league.lastMatch.get

      openLigaMatch.id must beGreaterThan(100l)

      openLigaMatch.finalResult.isDefined must beTrue

      openLigaMatch.finalResult.get.name must beEqualTo("Endergebnis")

      openLigaMatch.finalResult.get.scoreTeam1 must beGreaterThanOrEqualTo(2)

      openLigaMatch.finalResult.get.scoreTeam2 must beGreaterThanOrEqualTo(1)

    }

    "nextMatch " >> {

      val league = League("bl1")

      val openLigaMatchOption = league.nextMatch

      if (openLigaMatchOption.isDefined) {
        val openLigaMatch = openLigaMatchOption.get
        openLigaMatch.id must beGreaterThanOrEqualTo(28957l)
      } else {
        league.currentMatchGroup.matchGroupOrderNumber must beEqualTo(34)
      }

    }

    "nextMatch where no next match exists" >> {

      val league = League("wm14")

      val openLigaMatch = league.nextMatch

      openLigaMatch must beNone

    }

    "lastMatch where no next match exists" >> {

      val league = League("WMTipp18_1")

      val openLigaMatch = league.lastMatch

      openLigaMatch must beNone

    }

    "matches between 02.03.2017 11:30 - 04.03.2017 10:00 " >> {

      val league = League("bl1")

      val startDate: DateTime = new DateTime("2017-03-02T11:30Z")
      val endDate: DateTime = new DateTime("2017-04-04T11:30Z")

      val openLigaMatch = league.matchesBetween(startDate, endDate)

      openLigaMatch.size must beGreaterThanOrEqualTo(36)
    }
  }


}
