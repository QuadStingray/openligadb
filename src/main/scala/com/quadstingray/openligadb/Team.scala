package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.{OpenligaDbSOAPService, OpenligaDbService}

case class Team(id: Long, name: String, shortname: String, iconUrl: String) {

  def lastMatch(season: Season): Option[MatchData] = OpenligaDbSOAPService.getLastMatchForSeasonAndTeam(season.id, id)

  def nextMatch(season: Season): Option[MatchData] = OpenligaDbSOAPService.getNextMatchForSeasonAndTeam(season.id, id)

  def lastMatchesVs(team: Team): List[MatchData] = OpenligaDbService.getMatchdataByTeams(id, team.id)

}

object Team {
  def apply(id: Long): Team = new Team(id, null, null, null)
}