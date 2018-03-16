package com.quadstingray.openligadb

object OpenligaDb extends OpenligaDbTrait {

  def availableLeagues: List[League] = openligaDbSOAPService.getAvailLeagues.sortWith((l1, l2) => l1.shortName < l2.shortName)

  def availableSeasons: List[Season] = openligaDbSOAPService.getAllSeasons

  def availableSports: List[Sport] = openligaDbSOAPService.getAllSports

}
