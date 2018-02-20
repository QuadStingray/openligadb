package com.quadstingray.openligadb

case class Goal(id: Long, matchId: Long, player: Player, scoreTeam1: Int, scoreTeam2: Int,  matchMinute: Option[Int], isOvertime: Option[Boolean], isOwnGoal: Option[Boolean], isPenalty: Option[Boolean]) {

  def matchData: MatchData = MatchData(matchId)

}
