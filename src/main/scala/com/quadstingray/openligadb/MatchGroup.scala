package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoMatchGroupFoundException
import com.quadstingray.openligadb.services.{OpenligaDbSOAPService, OpenligaDbService}
import org.joda.time.DateTime

case class MatchGroup(id: Long, season: Season, name: String, matchGroupOrderNumber: Int) {

  def matches: List[MatchData] = OpenligaDbService.getMatchdataBySeasonAndGroup(season.league.shortName, season.year, matchGroupOrderNumber)

  def lastChangeDate: DateTime = OpenligaDbSOAPService.getLastChangeDateForMatchGroup(season.league.shortName, season.year, matchGroupOrderNumber)

}

object MatchGroup {
  def apply(season: Season, matchGroupOrderNumber: Int): MatchGroup = {
    try {
      season.matchGroups.filter(matchGroup => matchGroup.matchGroupOrderNumber == matchGroupOrderNumber).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoMatchGroupFoundException()
    }
  }

  def apply(id: Long, season: Season): MatchGroup = {
    try {
      season.matchGroups.filter(matchGroup => matchGroup.id == id).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoMatchGroupFoundException()
    }
  }

}