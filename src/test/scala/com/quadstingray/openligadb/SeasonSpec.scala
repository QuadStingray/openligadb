package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSeasonFoundException
import org.joda.time.DateTime

class SeasonSpec extends org.specs2.mutable.Specification {

  "Season" >> {

    "apply with id 3005" >> {

      val season = Season(3005)

      season.year must beEqualTo(2016)

      season.name must beEqualTo("1. Fußball-Bundesliga 2016/2017")

      season.id must beEqualTo(3005l)

    }

    "apply with id 12345678912345678 NoSeasonFoundException" >> {

      var errorCatched = false

      try {
        val season = Season(12345678912345678L)
      } catch {
        case e: NoSeasonFoundException =>
          errorCatched = true
      }

      errorCatched must beTrue

    }

    "apply with shortName bl1 and year 2015 NoSeasonFoundException" >> {

      var errorCatched = false

      try {
        val season = Season("bl1", 2099)
      } catch {
        case e: NoSeasonFoundException =>
          errorCatched = true
      }

      errorCatched must beTrue

    }

    "apply with shortName bl1 and year 2015" >> {

      val season = Season("bl1", 2015)

      season.year must beEqualTo(2015)

      season.name must beEqualTo("1. Fußball-Bundesliga 2015/2016")

      season.id must beEqualTo(848l)

      season.lastChangeDate must beEqualTo(new DateTime("2016-05-18T16:22:40.253"))

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

    "old season get current matchgroup" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      val matchGroup = season.currentMatchGroup

      matchGroup.season must beEqualTo(season)

      matchGroup.matchGroupOrderNumber must beEqualTo(34)
    }

    "current bl season get current matchgroup" >> {

      val league = League("bl1")

      val matchGroup = league.currentSeason.currentMatchGroup

      matchGroup.matchGroupOrderNumber must between(1, 34)

      matchGroup must beEqualTo(league.currentMatchGroup)
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

      match1.team1.shortname must beEqualTo("FCB")

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

      fcb.name must beEqualTo("FC Bayern")

      fcb.shortname must beEqualTo("FCB")

      fcb.iconUrl.contains("FC_Bayern_M") must beTrue

      fcb.iconUrl.contains("Logo") must beTrue

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

    "get current table" >> {

      val season = Season(848, League("bl1"), 2015, "Was auch immer fuer ein Name")

      val table: List[TableElement] = season.currentTable

      table.size must beEqualTo(18)

      val seasonWinner = table.head

      val winningTeamId = seasonWinner.team.id

      //      Team(winningTeamId) must beEqualTo(seasonWinner.team)

      seasonWinner.team.name must beEqualTo("FC Bayern")

      seasonWinner.countMatches must beEqualTo(34)

      seasonWinner.win must beEqualTo(28)

      seasonWinner.draw must beEqualTo(4)

      seasonWinner.defeat must beEqualTo(2)

      seasonWinner.goals must beEqualTo(80)

      seasonWinner.opponentGoals must beEqualTo(17)

      seasonWinner.goalDiff must beEqualTo(63)

      seasonWinner.points must beEqualTo(88)

      val seasonLast = table.last

      seasonLast.team.name must beEqualTo("Hannover 96")

      seasonLast.countMatches must beEqualTo(34)

      seasonLast.win must beEqualTo(7)

      seasonLast.draw must beEqualTo(4)

      seasonLast.defeat must beEqualTo(23)

      seasonLast.goals must beEqualTo(31)

      seasonLast.opponentGoals must beEqualTo(62)

      seasonLast.goalDiff must beEqualTo(-31)

      seasonLast.points must beEqualTo(25)

    }
  }


}
