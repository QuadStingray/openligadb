package com.quadstingray.openligadb

case class MatchResult(id: Long, matchId: Long, name: String, scoreTeam1: Int, scoreTeam2: Int, resultOrderID: Int, typeId: Int, description: String) {

  def matchData: MatchData = MatchData(matchId)

}
