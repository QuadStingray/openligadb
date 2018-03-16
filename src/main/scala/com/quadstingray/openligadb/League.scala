package com.quadstingray.openligadb

import org.joda.time.DateTime

case class League(shortName: String) extends OpenligaDbTrait {

  def seasons: List[Season] = openligaDbSOAPService.getAllSeasons(shortName).sortWith((s1, s2) => s1.year > s2.year)

  def currentSeason: Season = currentMatches.head.matchGroup.season

  def currentMatchGroup: MatchGroup = openligaDbService.getCurrentMatchGroup(this)

  def currentMatches: List[MatchData] = openligaDbService.getCurrentMatches(shortName)

  def lastMatch: Option[MatchData] = openligaDbSOAPService.getLastMatchForLeague(shortName)

  def nextMatch: Option[MatchData] = openligaDbSOAPService.getNextMatchForLeague(shortName)

  def matchesBetween(start: DateTime, end: DateTime): List[MatchData] = openligaDbSOAPService.getMatchesBetween(shortName, start, end)

  def season(year: Int): Season = seasons.filter(season => season.year == year).head
}
