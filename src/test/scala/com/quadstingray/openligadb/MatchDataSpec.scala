package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoMatchDataFoundException
import org.joda.time.DateTime


class MatchDataSpec extends org.specs2.mutable.Specification {

  "Match" >> {

    "apply with id 39738" >> {

      val game = MatchData(39738)

      game.id must beEqualTo(39738)

      game.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game.isFinished must beTrue

      game.goals.size must beEqualTo(1)

      val goalForTest = game.goals.head

      goalForTest.matchData must beEqualTo(game)

      goalForTest.matchMinute.get must beEqualTo(11)

      goalForTest.id must beEqualTo(55579)

      goalForTest.isOvertime.get must beFalse

      goalForTest.isOwnGoal.get must beFalse

      goalForTest.isPenalty.get must beFalse

      goalForTest.scoreTeam1 must beEqualTo(1)

      goalForTest.scoreTeam2 must beEqualTo(0)

      goalForTest.player.id must beEqualTo(16033)

      goalForTest.player.nationality must beNone

      goalForTest.player.name must beEqualTo("Pierre-Emerick Aubameyang")

      game.matchGroup.id must beEqualTo(23843)

      game.matchGroup.name must beEqualTo("11. Spieltag")

      game.matchGroup.matchGroupOrderNumber must beEqualTo(11)

      game.lastUpdateDateTime must beEqualTo(new DateTime("2016-11-24T19:57:28.54"))

      game.matchGroup.season.id must beEqualTo(3005)

      game.matchGroup.season.name must beEqualTo("1. Fußball-Bundesliga 2016/2017")

      game.location.get.id must beEqualTo(184)

      game.location.get.city must beEqualTo("Dortmund")

      game.location.get.stadium must beEqualTo("Signal-Iduna-Park")

      game.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game.finalResult.get.scoreTeam1 must beEqualTo(1)

      game.finalResult.get.scoreTeam2 must beEqualTo(0)

      game.matchResults.size must beEqualTo(2)

      val halfTimeResult = game.matchResults.head

      halfTimeResult.matchData must beEqualTo(game)

      halfTimeResult.id must beEqualTo(70293)

      halfTimeResult.resultOrderID must beEqualTo(1)

      halfTimeResult.typeId must beEqualTo(1)

      halfTimeResult.scoreTeam1 must beEqualTo(1)

      halfTimeResult.scoreTeam2 must beEqualTo(0)

      halfTimeResult.description must beEqualTo("Ergebnis zur Halbzeit")

      halfTimeResult.name must beEqualTo("Halbzeitergebnis")

      game.numberOfViewers.get must beEqualTo(81360)

      game.team1.id must beEqualTo(7)

      game.team1.shortname must beEqualTo("BVB")

      game.team1.iconUrl.contains("Borussia_Dortmund_logo") must beTrue

      game.team1.iconUrl.contains(".png") must beTrue

      game.team1.name must beEqualTo("Borussia Dortmund")

      game.team2.id must beEqualTo(40)

      game.team2.shortname must beEqualTo("FC Bayern")

      game.team2.iconUrl.contains("Logo_FC_Bayern_M") must beTrue

      game.team2.iconUrl.contains(".png") must beTrue

      game.team2.name must beEqualTo("Bayern München")
    }

    "apply with id 12345678912345678 NoMatchDataFoundException" >> {

      var errorCatched = false

      try {
        val season = MatchData(12345678912345678L)
      } catch {
        case e: NoMatchDataFoundException =>
          errorCatched = true
      }

      errorCatched must beTrue

    }

  }


}
