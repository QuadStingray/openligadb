package com.quadstingray.openligadb.services

import com.quadstingray.openligadb._
import org.joda.time.DateTime

import scala.collection.mutable.ArrayBuffer

private[openligadb] trait OpenligaDbImplicits {

  implicit protected def convertOpenligaDbMatchToMatch(openligaDbMatch: OpenligaDbMatch): Option[Match] = {
    val listOfMatchResults: List[MatchResult] = openligaDbMatch.MatchResults

    val finalResult: Option[MatchResult] =
      if (listOfMatchResults.nonEmpty) {
        Option(listOfMatchResults.sortWith((result1, result2) => result1.resultOrderID > result2.resultOrderID).head)
      } else {
        None
      }

    val matchDateTime = new DateTime(openligaDbMatch.MatchDateTimeUTC)

    val lastUpdateDateTime = new DateTime(openligaDbMatch.LastUpdateDateTime)

    if (openligaDbMatch.MatchID == 0) {
      None
    } else {
      val season = Season(openligaDbMatch.LeagueId)
      Some(Match(openligaDbMatch.MatchID, openligaDbMatch.Team1, openligaDbMatch.Team2, openligaDbMatch.Goals, finalResult, listOfMatchResults, (openligaDbMatch.Group, season), openligaDbMatch.Location, openligaDbMatch.NumberOfViewers, openligaDbMatch.MatchIsFinished, matchDateTime, lastUpdateDateTime))
    }
  }

  implicit protected def convertListOpenligaDbMatchToListMatch(openligaDbMatch: List[OpenligaDbMatch]): List[Match] = {
    val buffer: ArrayBuffer[Match] = ArrayBuffer()
    openligaDbMatch.foreach(myMatch => myMatch.foreach(game => buffer += game))
    buffer.toList
  }

  implicit protected def convertListOpenligaDbMatchResultToListMatchResult(openligaDbMatchResult: List[OpenligaDbMatchResult]): List[MatchResult] = {
    val buffer: ArrayBuffer[MatchResult] = ArrayBuffer()
    openligaDbMatchResult.foreach(myMatch => buffer += myMatch)
    buffer.toList
  }

  implicit protected def convertOpenligaDbMatchResultToMatchResult(openligaDbMatchResult: OpenligaDbMatchResult): MatchResult = {
    MatchResult(openligaDbMatchResult.ResultID, openligaDbMatchResult.ResultName, openligaDbMatchResult.PointsTeam1, openligaDbMatchResult.PointsTeam2, openligaDbMatchResult.ResultOrderID, openligaDbMatchResult.ResultTypeID, openligaDbMatchResult.ResultDescription)
  }

  implicit protected def convertOpenligaDbTeamToTeam(openligaDbTeam: OpenligaDbTeam): Team = {
    Team(openligaDbTeam.TeamId, openligaDbTeam.TeamName, openligaDbTeam.ShortName, openligaDbTeam.TeamIconUrl)
  }

  implicit protected def convertListOpenligaDbTeamToListTeam(openligaDbTeamList: List[OpenligaDbTeam]): List[Team] = {
    val buffer: ArrayBuffer[Team] = ArrayBuffer()
    openligaDbTeamList.foreach(openligaDbTeam => buffer += openligaDbTeam)
    buffer.toList
  }

  implicit protected def convertOpenligaDbLocationToLocation(optionOpenligaDbLocation: Option[OpenligaDbLocation]): Option[Location] = {
    if (optionOpenligaDbLocation.isDefined) {
      val openligaDbLocation = optionOpenligaDbLocation.get
      Some(Location(openligaDbLocation.LocationID, openligaDbLocation.LocationStadium, openligaDbLocation.LocationCity))
    } else {
      None
    }
  }

  implicit protected def convertOpenligaDbGoalToGoal(openligaDbGoal: OpenligaDbGoal): Goal = {
    Goal(openligaDbGoal.GoalID, Player(openligaDbGoal.GoalGetterID, openligaDbGoal.GoalGetterName), openligaDbGoal.MatchMinute, openligaDbGoal.ScoreTeam1, openligaDbGoal.ScoreTeam2, openligaDbGoal.IsOvertime, openligaDbGoal.IsOwnGoal, openligaDbGoal.IsPenalty)
  }


  implicit protected def convertListOpenligaDbGoalToListGoal(openligaDbGoalList: List[OpenligaDbGoal]): List[Goal] = {
    val buffer: ArrayBuffer[Goal] = ArrayBuffer()
    openligaDbGoalList.foreach(openligaDbTeam => buffer += openligaDbTeam)
    buffer.toList
  }

  implicit protected def convertOpenligaDbLeagueToLeague(openligaDbLeague: OpenligaDbSoapLeague): League = {
    League(openligaDbLeague.leagueShortcut)
  }

  implicit protected def convertListOpenligaDbLeagueResultToListLeague(openligaDbLeagueList: List[OpenligaDbSoapLeague]): List[League] = {
    val buffer: ArrayBuffer[League] = ArrayBuffer()
    openligaDbLeagueList.foreach(dbLeague => buffer += dbLeague)
    buffer.toList
  }

  implicit protected def convertOpenligaDbLeagueToSeason(openligaDbLeague: OpenligaDbSoapLeague): Season = {
    Season(openligaDbLeague.leagueID.toInt, openligaDbLeague, openligaDbLeague.leagueSaison.toInt, openligaDbLeague.leagueName)
  }

  implicit protected def convertListOpenligaDbLeagueGroupToListSeasons(openligaDbSeasonList: List[OpenligaDbSoapLeague]): List[Season] = {
    val buffer: ArrayBuffer[Season] = ArrayBuffer()
    openligaDbSeasonList.foreach(openligaDbLeague => buffer += openligaDbLeague)
    buffer.toList
  }

  implicit protected def convertOpenligaDbMatchGroupToMatchGroup(isd: (OpenligaDbGroup, Season)): MatchGroup = {
    MatchGroup(isd._1.GroupID, isd._2, isd._1.GroupName, isd._1.GroupOrderID)
  }

  implicit protected def convertListOpenligaDbMatchGroupToListMatchGroup(isd: (List[OpenligaDbGroup], Season)): List[MatchGroup] = {
    val buffer: ArrayBuffer[MatchGroup] = ArrayBuffer()
    isd._1.foreach(openligaDbGroup => {
      val matchGroup: MatchGroup = (openligaDbGroup, isd._2)
      buffer += matchGroup
    })
    buffer.toList
  }

  implicit protected def convertOpenligaDbSportToSport(openligaDbSport: OpenligaDbSoapSport): Sport = {
    Sport(openligaDbSport.sportsID.toLong, openligaDbSport.sportsName)
  }

  implicit protected def convertListOpenligaDbSportToListSport(openligaDbSportList: List[OpenligaDbSoapSport]): List[Sport] = {
    val buffer: ArrayBuffer[Sport] = ArrayBuffer()
    openligaDbSportList.foreach(openligaDbSport => buffer += openligaDbSport)
    buffer.toList
  }

  implicit protected def convertOpenligaDbGoalGetterToGoalGetter(openligaDbGoalGetter: OpenligaDbGoalGetter): GoalGetter = {
    GoalGetter(Player(openligaDbGoalGetter.GoalGetterId, openligaDbGoalGetter.GoalGetterName, openligaDbGoalGetter.GoalGetterNationality), openligaDbGoalGetter.GoalCount)
  }

  implicit protected def convertListOpenligaDbGoalGetterToListGoalGetter(openligaDbGoalGetterList: List[OpenligaDbGoalGetter]): List[GoalGetter] = {
    val buffer: ArrayBuffer[GoalGetter] = ArrayBuffer()
    openligaDbGoalGetterList.foreach(openligaDbGroup => buffer += openligaDbGroup)
    buffer.toList
  }
}

