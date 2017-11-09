package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.{OpenligaDbSOAPService, OpenligaDbService}

case class Team(id: Long, name: String, shortname: String, iconUrl: String) {

  def getLastMatch(season: Season): Option[Match] = OpenligaDbSOAPService.getLastMatchForSeasonAndTeam(season.id, id)

  def getNextMatch(season: Season): Option[Match] = OpenligaDbSOAPService.getNextMatchForSeasonAndTeam(season.id, id)

  def getLastMatchesVs(team: Team): List[Match] = OpenligaDbService.getMatchdataByTeams(id, team.id)

}

object Team {
  def apply(id: Long): Team = new Team(id, null, null, null)
}