package com.quadstingray.openligadb

class MatchDataGroupSpec extends org.specs2.mutable.Specification {

  "MatchGroup" >> {

    "apply with id 17757" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      val matchDay = MatchGroup(17757, season)

      matchDay.id must beEqualTo(17757l)

      matchDay.matchGroupOrderNumber must beEqualTo(8)

      matchDay.name must beEqualTo("8. Spieltag")

    }

    "apply with shortName bl1 and year 2015" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      val matchDay = MatchGroup(season, 9)

      matchDay.id must beEqualTo(17758l)

      matchDay.matchGroupOrderNumber must beEqualTo(9)

      matchDay.name must beEqualTo("9. Spieltag")

    }

    "getMatches" >> {
      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      val matchGroup = MatchGroup(17758, season, "Unbekannter Name", 9)

      val matches = matchGroup.matches

      matches.size must beEqualTo(9)

      val game = matches.head

      game.team1.name must beEqualTo("1. FSV Mainz 05")

      game.team2.shortname must beEqualTo("BVB")

      game.finalResult.get.scoreTeam1 must beEqualTo(0)

      game.finalResult.get.scoreTeam2 must beEqualTo(2)

    }

  }


}
