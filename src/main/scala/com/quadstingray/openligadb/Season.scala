package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSeasonFoundException
import com.quadstingray.openligadb.services.{OpenligaDbSOAPService, OpenligaDbService}
import org.joda.time.DateTime

case class Season(id: Long, league: League, year: Int, name: String) {

  def matchGroups: List[MatchGroup] = OpenligaDbService.getAvailGroups(this)

  def teams: List[Team] = OpenligaDbService.getAvailableTeams(league.shortName, year)

  def allMatches: List[MatchData] = OpenligaDbService.getAllMatchesForLeagueSeason(league.shortName, year)

  def goalGetters: List[GoalGetter] = OpenligaDbService.getGoalGetters(league.shortName, year).sortWith((gg1, gg2) => gg1.goalCount > gg2.goalCount)

  def allGoals: List[Goal] = OpenligaDbSOAPService.getGoalsForLeagueSeason(this.league.shortName, this.year)

  def currentMatchGroup: MatchGroup = {
    if (league.currentMatchGroup.season == this) {
      league.currentMatchGroup
    } else {
      matchGroups.last
    }
  }

  def lastChangeDate: DateTime = OpenligaDbSOAPService.getLastChangeDateForSeason(league.shortName, year)
}

object Season {

  def apply(id: Long): Season = {
    try {
      OpenligaDbSOAPService.getAllSeasons.filter(season => id == season.id).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoSeasonFoundException()
    }
  }

  def apply(leagueShortCode: String, year: Int): Season = {
    try {
      OpenligaDbSOAPService.getAllSeasons(leagueShortCode).filter(season => season.year == year).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoSeasonFoundException()
    }
  }

}
