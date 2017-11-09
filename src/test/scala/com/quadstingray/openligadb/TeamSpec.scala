package com.quadstingray.openligadb


class TeamSpec extends org.specs2.mutable.Specification {

  "Team" >> {

    "fcb.getNextMatch(currentSeason) " >> {

      val league = League("bl1")

      val fcb = Team(40)

      val nextMatch = fcb.getNextMatch(league.currentSeason)

      nextMatch.isDefined must beTrue

      nextMatch.get.id must beGreaterThanOrEqualTo(3000l)

    }

    "fcb.getLastMatch(currentSeason) " >> {

      val league = League("bl1")

      val fcb = Team(40)

      val nextMatch = fcb.getLastMatch(league.currentSeason)

      nextMatch.isDefined must beTrue

      nextMatch.get.id must beGreaterThanOrEqualTo(3000l)

    }

    "getLastMatchesVs for FC Bayern vs. BVB" >> {

      val fcb = Team(40)

      val bvb = Team(7)

      val matches = fcb.getLastMatchesVs(bvb)

      matches.size must beGreaterThan(50)
    }
  }


}
