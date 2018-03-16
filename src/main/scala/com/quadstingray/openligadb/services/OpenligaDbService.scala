package com.quadstingray.openligadb.services

import javax.inject.Inject

import com.quadstingray.openligadb._
import com.quadstingray.openligadb.exceptions.NoMatchDataFoundException
import org.json4s.DefaultFormats
import org.json4s.native.Serialization.read

import scala.collection.mutable.ArrayBuffer

trait OpenligaDbService {
  def getCurrentMatches(shortName: String): List[MatchData]

  def getCurrentMatchGroup(league: League): MatchGroup

  def getAllMatchesForLeagueSeason(shortName: String, year: Int): List[MatchData]

  def getAvailGroups(season: Season): List[MatchGroup]

  def getAvailableTeams(shortName: String, year: Int): List[Team]

  def getGoalGetters(shortName: String, year: Int): List[GoalGetter]

  def getMatchdataBySeasonAndGroup(shortName: String, year: Int, groupOrderId: Int): List[MatchData]

  def getTableBySeason(shortName: String, year: Int): List[TableElement]

  def getMatchdataById(id: Long): Option[MatchData]

  def getMatchdataByTeams(team1Id: Long, team2Id: Long): List[MatchData]

}

private[openligadb] class OpenligaDbServiceImpementation @Inject()(httpService: HttpService) extends OpenligaDbService with OpenligaDbImplicits {

  implicit val defaultFormats: DefaultFormats.type = DefaultFormats

  def getCurrentMatches(shortName: String): List[MatchData] = {
    read[List[OpenligaDbMatch]](httpService.get("https://www.openligadb.de/api/getmatchdata/%s".format(shortName)))
  }

  def getCurrentMatchGroup(league: League): MatchGroup = {
    val matchGroup = read[OpenligaDbGroup](httpService.get("https://www.openligadb.de/api/getcurrentgroup/%s".format(league.shortName)))
    val season = league.currentSeason
    (matchGroup, season)
  }

  def getAllMatchesForLeagueSeason(shortName: String, year: Int): List[MatchData] = {
    read[List[OpenligaDbMatch]](httpService.get("https://www.openligadb.de/api/getmatchdata/%s/%s".format(shortName, year)))
  }

  def getAvailGroups(season: Season): List[MatchGroup] = {
    (read[List[OpenligaDbGroup]](httpService.get("https://www.openligadb.de/api/getavailablegroups/%s/%s".format(season.league.shortName, season.year))), season)
  }

  def getAvailableTeams(shortName: String, year: Int): List[Team] = {
    read[List[OpenligaDbTeam]](httpService.get("https://www.openligadb.de/api/getavailableteams/%s/%s".format(shortName, year)))
  }

  def getGoalGetters(shortName: String, year: Int): List[GoalGetter] = {
    read[List[OpenligaDbGoalGetter]](httpService.get("https://www.openligadb.de/api/getgoalgetters/%s/%s".format(shortName, year)))
  }

  def getMatchdataBySeasonAndGroup(shortName: String, year: Int, groupOrderId: Int): List[MatchData] = {
    read[List[OpenligaDbMatch]](httpService.get("https://www.openligadb.de/api/getmatchdata/%s/%s/%s".format(shortName, year, groupOrderId)))
  }

  def getTableBySeason(shortName: String, year: Int): List[TableElement] = {
    val tableSeason = read[List[OpenligaDbTableItem]](httpService.get("https://www.openligadb.de/api/getbltable/%s/%s".format(shortName, year)))
    val result: ArrayBuffer[TableElement] = ArrayBuffer()
    var i = 1
    tableSeason.foreach(element => {
      result += TableElement(i, Team(element.TeamInfoId, element.TeamName, element.ShortName, element.TeamIconUrl), element.Points, element.Matches, element.Won, element.Draw, element.Lost, element.Goals, element.OpponentGoals)
      i += 1
    })
    result.toList
  }

  def getMatchdataById(id: Long): Option[MatchData] = {
    val resultstring = httpService.get("https://www.openligadb.de/api/getmatchdata/%s".format(id))
    if (resultstring.equalsIgnoreCase("[]"))
      throw new NoMatchDataFoundException()

    read[OpenligaDbMatch](resultstring)
  }

  def getMatchdataByTeams(team1Id: Long, team2Id: Long): List[MatchData] = {
    read[List[OpenligaDbMatch]](httpService.get("https://www.openligadb.de/api/getmatchdata/%s/%s".format(team1Id, team2Id)))
  }

}

