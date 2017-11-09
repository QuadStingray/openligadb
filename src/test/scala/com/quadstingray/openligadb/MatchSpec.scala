package com.quadstingray.openligadb

import org.joda.time.DateTime


class MatchSpec extends org.specs2.mutable.Specification {

  "Match" >> {

    "apply with id 39738" >> {

      val game = Match(39738)

      game.id must beEqualTo(39738)

      game.matchDateTime must beEqualTo(new DateTime("2016-11-19T17:30:00Z"))

      game.isFinished must beTrue

      game.goals.size must beEqualTo(1)

      val goalForTest = game.goals.head

      goalForTest.matchMinute must beEqualTo(11)

      goalForTest.id must beEqualTo(55579)

      goalForTest.isOvertime must beFalse

      goalForTest.isOwnGoal must beFalse

      goalForTest.isPenalty must beFalse

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

      game.team1.iconUrl must beEqualTo("https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Borussia_Dortmund_logo.svg/20px-Borussia_Dortmund_logo.svg.png")

      game.team1.name must beEqualTo("Borussia Dortmund")

      game.team2.id must beEqualTo(40)

      game.team2.shortname must beEqualTo("FC Bayern")

      game.team2.iconUrl must beEqualTo("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg/20px-Logo_FC_Bayern_M%C3%BCnchen.svg.png")

      game.team2.name must beEqualTo("Bayern München")
    }

  }


}
