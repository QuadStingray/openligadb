package com.quadstingray.openligadb.services

import com.quadstingray.openligadb._
import org.json4s.native.Serialization.read

private[openligadb] object OpenligaDbService extends HttpService with OpenligaDbImplicits {

  def getCurrentMatches(shortName: String): List[MatchData] = {
    read[List[OpenligaDbMatch]](get("https://www.openligadb.de/api/getmatchdata/%s".format(shortName)))
  }

  def getCurrentMatchGroup(league: League): MatchGroup = {
    val matchGroup = read[OpenligaDbGroup](get("https://www.openligadb.de/api/getcurrentgroup/%s".format(league.shortName)))
    val season = league.currentSeason
    (matchGroup, season)
  }

  def getAllMatchesForLeagueSeason(shortName: String, year: Int): List[MatchData] = {
    read[List[OpenligaDbMatch]](get("https://www.openligadb.de/api/getmatchdata/%s/%s".format(shortName, year)))
  }

  def getAvailGroups(season: Season): List[MatchGroup] = {
    (read[List[OpenligaDbGroup]](get("https://www.openligadb.de/api/getavailablegroups/%s/%s".format(season.league.shortName, season.year))), season)
  }

  def getAvailableTeams(shortName: String, year: Int): List[Team] = {
    read[List[OpenligaDbTeam]](get("https://www.openligadb.de/api/getavailableteams/%s/%s".format(shortName, year)))
  }

  def getGoalGetters(shortName: String, year: Int): List[GoalGetter] = {
    read[List[OpenligaDbGoalGetter]](get("https://www.openligadb.de/api/getgoalgetters/%s/%s".format(shortName, year)))
  }

  def getMatchdataBySeasonAndGroup(shortName: String, year: Int, groupOrderId: Int): List[MatchData] = {
    read[List[OpenligaDbMatch]](get("https://www.openligadb.de/api/getmatchdata/%s/%s/%s".format(shortName, year, groupOrderId)))
  }

  def getMatchdataById(id: Long): Option[MatchData] = {
    read[OpenligaDbMatch](get("https://www.openligadb.de/api/getmatchdata/%s".format(id)))
  }

  def getMatchdataByTeams(team1Id: Long, team2Id: Long): List[MatchData] = {
    read[List[OpenligaDbMatch]](get("https://www.openligadb.de/api/getmatchdata/%s/%s".format(team1Id, team2Id)))
  }

  def getLastChangeDate(team1Id: Long, team2Id: Long): List[MatchData] = {
    read[List[OpenligaDbMatch]](get("https://www.openligadb.de/api/getmatchdata/%s/%s".format(team1Id, team2Id)))
  }

}

