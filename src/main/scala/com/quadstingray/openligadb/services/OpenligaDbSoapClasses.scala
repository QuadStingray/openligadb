package com.quadstingray.openligadb.services

private case class OpenligaDbSoapLeague(leagueID: String, leagueSportID: String, leagueName: String, leagueShortcut: String, leagueSaison: String)

private case class OpenligaDbSoapSport(sportsID: String, sportsName: String)

private case class OpenligaDbSoapMatchHelper(matchID: String)

private case class OpenligaDbSoapGoal(goalID: String, goalScoreTeam1: String, goalScoreTeam2: String, goalMatchMinute: String = "-1", goalGetterID: String, goalGetterName: String = "", goalComment: String = "", goalMachID : String = "", goalOvertime: String,
                                      goalOwnGoal: String, goalPenalty: String)
