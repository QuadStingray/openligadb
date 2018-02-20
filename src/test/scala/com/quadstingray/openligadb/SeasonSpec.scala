package com.quadstingray.openligadb

class SeasonSpec extends org.specs2.mutable.Specification {

  "Season" >> {

    "apply with id 3005" >> {

      val season = Season(3005)

      season.year must beEqualTo(2016)

      season.name must beEqualTo("1. Fußball-Bundesliga 2016/2017")

      season.id must beEqualTo(3005l)

    }

    "apply with shortName bl1 and year 2015" >> {

      val season = Season("bl1", 2015)

      season.year must beEqualTo(2015)

      season.name must beEqualTo("1. Fußball-Bundesliga 2015/2016")

      season.id must beEqualTo(848l)

    }

    "getMatchGroups" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      season.year must beEqualTo(2015)

      season.name must beEqualTo("Was auch immer fuer ein Name")

      season.id must beEqualTo(848l)

      val matchGroups = season.matchGroups

      matchGroups.size must beEqualTo(34)

      val matchDay1 = matchGroups.head

      matchDay1.id must beEqualTo(17750)

      matchDay1.name must beEqualTo("1. Spieltag")

      matchDay1.matchGroupOrderNumber must beEqualTo(1)

    }

    "getAllMatches" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      season.year must beEqualTo(2015)

      season.name must beEqualTo("Was auch immer fuer ein Name")

      season.id must beEqualTo(848l)

      val matches = season.allMatches

      matches.size must beEqualTo(306)

      val match1 = matches.head

      match1.id must beEqualTo(33236)

      match1.team1.shortname must beEqualTo("FC Bayern")

      match1.team2.shortname must beEqualTo("HSV")

      match1.finalResult.isDefined must beTrue

      val finalResult = match1.finalResult.get

      finalResult.scoreTeam1 must beEqualTo(5)

      finalResult.scoreTeam2 must beEqualTo(0)
    }

    "getTeams" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      season.year must beEqualTo(2015)

      season.name must beEqualTo("Was auch immer fuer ein Name")

      season.id must beEqualTo(848l)

      val teams = season.teams

      teams.size must beEqualTo(18)

      val fcb = teams.filter(team => team.id == 40).head

      fcb.id must beEqualTo(40)

      fcb.name must beEqualTo("Bayern München")

      fcb.shortname must beEqualTo("FC Bayern")

      fcb.iconUrl.contains("Logo_FC_Bayern_M") must beTrue

      fcb.iconUrl.contains(".png") must beTrue

    }

    "getGoalGetters" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      season.year must beEqualTo(2015)

      season.name must beEqualTo("Was auch immer fuer ein Name")

      season.id must beEqualTo(848l)

      val goalGetters = season.goalGetters

      goalGetters.size must beEqualTo(290)

      val lewy = goalGetters.filter(goalGetter => goalGetter.player.id == 1478).head

      lewy.player.id must beEqualTo(1478)

      lewy.player.name must beEqualTo("Lewandowski")

      lewy.player.nationality must beNone

      lewy.goalCount must beEqualTo(28)

    }

    "getAllGoals" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      val allGoals = season.allGoals

      allGoals.size must beEqualTo(869)

      val fistGoalOfSeason = allGoals.head

      fistGoalOfSeason.player.name must beEqualTo("Benatia, Medhi")



    }

  }


}
