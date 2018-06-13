package com.quadstingray.openligadb


class TeamSpec extends org.specs2.mutable.Specification {

  "Team" >> {

    "fcb.getNextMatch(currentSeason) " >> {

      val league = League("bl1")

      val fcb = Team(40)

      val nextMatch = fcb.nextMatch(league.currentSeason)

      nextMatch.isDefined must beTrue

      nextMatch.get.id must beGreaterThanOrEqualTo(3000l)

    }

    "fcb.getLastMatch(currentSeason) " >> {

      val league = League("bl1")

      val fcb = Team(40)

      val nextMatch = fcb.lastMatch(league.currentSeason)

      if (nextMatch.isDefined) {
        nextMatch.isDefined must beTrue

        nextMatch.get.id must beGreaterThanOrEqualTo(3000l)

      } else {
        league.currentMatchGroup.matchGroupOrderNumber must beEqualTo(34)
      }

    }

    "getLastMatchesVs for FC Bayern vs. BVB" >> {

      val fcb = Team(40)

      val bvb = Team(7)

      val matches = fcb.lastMatchesVs(bvb)

      matches.size must beGreaterThan(50)
    }
  }


}
