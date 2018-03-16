package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSeasonFoundException
import org.joda.time.DateTime

case class Season(id: Long, league: League, year: Int, name: String) extends OpenligaDbTrait {

  def matchGroups: List[MatchGroup] = openligaDbService.getAvailGroups(this)

  def teams: List[Team] = openligaDbService.getAvailableTeams(league.shortName, year)

  def allMatches: List[MatchData] = openligaDbService.getAllMatchesForLeagueSeason(league.shortName, year)

  def goalGetters: List[GoalGetter] = openligaDbService.getGoalGetters(league.shortName, year).sortWith((gg1, gg2) => gg1.goalCount > gg2.goalCount)

  def allGoals: List[Goal] = openligaDbSOAPService.getGoalsForLeagueSeason(this.league.shortName, this.year)

  def currentMatchGroup: MatchGroup = {
    if (league.currentMatchGroup.season == this) {
      league.currentMatchGroup
    } else {
      matchGroups.last
    }
  }

  def currentTable: List[TableElement] = openligaDbService.getTableBySeason(league.shortName, year)

  def lastChangeDate: DateTime = openligaDbSOAPService.getLastChangeDateForSeason(league.shortName, year)
}

object Season extends OpenligaDbTrait {

  def apply(id: Long): Season = {
    try {
      openligaDbSOAPService.getAllSeasons.filter(season => id == season.id).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoSeasonFoundException()
    }
  }

  def apply(leagueShortCode: String, year: Int): Season = {
    try {
      openligaDbSOAPService.getAllSeasons(leagueShortCode).filter(season => season.year == year).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoSeasonFoundException()
    }
  }

}
