package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSportFoundException
import com.quadstingray.openligadb.services.OpenligaDbSOAPService

case class Sport(id: Long, name: String) {

  def getLeagues: List[League] = OpenligaDbSOAPService.getAvailLeaguesBySportId(id)

  def getSeasons: List[Season] = OpenligaDbSOAPService.getAvailSeasonsBySportId(id)

}

object Sport {
  def apply(id: Long): Sport = {

    try {
      OpenligaDb.getAvailableSports.filter(sport => sport.id == id).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoSportFoundException()
    }

  }
}