package com.quadstingray.openligadb.services

private case class OpenligaDbSoapLeague(leagueID: String, leagueSportID: String, leagueName: String, leagueShortcut: String, leagueSaison: String)

private case class OpenligaDbSoapSport(sportsID: String, sportsName: String)

private case class OpenligaDbSoapMatchHelper(matchID: String)