package com.quadstingray.openligadb

case class TableElement(leaguePosition: Long, team: Team, points: Int, countMatches: Int, win: Int, draw: Int, defeat: Int, goals: Int, opponentGoals: Int) {
  def goalDiff: Int = goals - opponentGoals
}
