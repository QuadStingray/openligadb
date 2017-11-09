package com.quadstingray.openligadb

import com.quadstingray.openligadb.services.OpenligaDbSOAPService

object OpenligaDb {

  def getAvailableLeagues: List[League] = OpenligaDbSOAPService.getAvailLeagues.sortWith((l1, l2) => l1.shortName < l2.shortName)

  def getAvailableSeasons: List[Season] = OpenligaDbSOAPService.getAllSeasons

  def getAvailableSports: List[Sport] = OpenligaDbSOAPService.getAllSports

}
