package com.quadstingray.openligadb

case class Team(id: Long, name: String, shortname: String, iconUrl: String) extends OpenligaDbTrait {

  def lastMatch(season: Season): Option[MatchData] = openligaDbSOAPService.getLastMatchForSeasonAndTeam(season.id, id)

  def nextMatch(season: Season): Option[MatchData] = openligaDbSOAPService.getNextMatchForSeasonAndTeam(season.id, id)

  def lastMatchesVs(team: Team): List[MatchData] = openligaDbService.getMatchdataByTeams(id, team.id)

}

object Team {
  def apply(id: Long): Team = new Team(id, null, null, null)
}