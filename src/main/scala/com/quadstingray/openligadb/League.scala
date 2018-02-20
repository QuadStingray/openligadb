package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.{OpenligaDbSOAPService, OpenligaDbService}
import org.joda.time.DateTime

case class League(shortName: String) {

  def seasons: List[Season] = OpenligaDbSOAPService.getAllSeasons(shortName).sortWith((s1, s2) => s1.year > s2.year)

  def currentSeason: Season = currentMatches.head.matchGroup.season

  def currentMatchGroup: MatchGroup = OpenligaDbService.getCurrentMatchGroup(this)

  def currentMatches: List[MatchData] = OpenligaDbService.getCurrentMatches(shortName)

  def lastMatch: Option[MatchData] = OpenligaDbSOAPService.getLastMatchForLeague(shortName)

  def nextMatch: Option[MatchData] = OpenligaDbSOAPService.getNextMatchForLeague(shortName)

  def matchesBetween(start: DateTime, end: DateTime): List[MatchData] = OpenligaDbSOAPService.getMatchesBetween(shortName, start, end)

  def season(year: Int): Season = seasons.filter(season => season.year == year).head
}
