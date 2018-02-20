package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.OpenligaDbSOAPService

object OpenligaDb {

  def availableLeagues: List[League] = OpenligaDbSOAPService.getAvailLeagues.sortWith((l1, l2) => l1.shortName < l2.shortName)

  def availableSeasons: List[Season] = OpenligaDbSOAPService.getAllSeasons

  def availableSports: List[Sport] = OpenligaDbSOAPService.getAllSports

}
