package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoSportFoundException

case class Sport(id: Long, name: String) extends OpenligaDbTrait {

  def leagues: List[League] = openligaDbSOAPService.getAvailLeaguesBySportId(id)

  def seasons: List[Season] = openligaDbSOAPService.getAvailSeasonsBySportId(id)

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