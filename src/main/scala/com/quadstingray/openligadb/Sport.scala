package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSportFoundException
import com.quadstingray.openligadb.services.OpenligaDbSOAPService

case class Sport(id: Long, name: String) {

  def leagues: List[League] = OpenligaDbSOAPService.getAvailLeaguesBySportId(id)

  def seasons: List[Season] = OpenligaDbSOAPService.getAvailSeasonsBySportId(id)

}

object Sport {
  def apply(id: Long): Sport = {

    try {
      OpenligaDb.availableSports.filter(sport => sport.id == id).head
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoSportFoundException()
    }

  }
}