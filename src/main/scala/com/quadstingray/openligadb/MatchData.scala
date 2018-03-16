package com.quadstingray.openligadb

import org.joda.time.DateTime

case class MatchData(id: Long, team1: Team, team2: Team, goals: List[Goal],
                     finalResult: Option[MatchResult], matchResults: List[MatchResult], matchGroup: MatchGroup,
                     location: Option[Location], numberOfViewers: Option[Int], isFinished: Boolean, matchDateTime: DateTime,
                     lastUpdateDateTime: DateTime
                    ) {


}


object MatchData extends OpenligaDbTrait {
  def apply(id: Long): MatchData = {
    val matchData = openligaDbService.getMatchdataById(id).get
    matchData.copy(goals = matchData.goals.map(goal => goal.copy(matchId = id))).copy(matchResults = matchData.matchResults.map(result => result.copy(matchId = id)))
  }
}