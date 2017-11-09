package com.quadstingray.openligadb.services

import com.quadstingray.openligadb.{League, Match, Season, Sport}
import org.joda.time.DateTime

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

private[openligadb] object OpenligaDbSOAPService extends HttpService with OpenligaDbImplicits {

  private def getAllOpenligaDbLeagues: List[OpenligaDbSoapLeague] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetAvailLeagues xmlns=\"http://msiggi.de/Sportsdata/Webservices\" />\n  </soap:Body>\n</soap:Envelope>"
    soap[List[OpenligaDbSoapLeague]](xmlString)
  }

  def getAvailLeagues: List[League] = {
    val resultMap: mutable.Map[String, League] = mutable.Map()
    getAllOpenligaDbLeagues.foreach(openLeague => {
      resultMap.put(openLeague.leagueShortcut, openLeague)
    })
    resultMap.values.toList
  }

  def getAllSeasons(shortName: String): List[Season] = getAllOpenligaDbLeagues.filter(league => shortName.toString.equalsIgnoreCase(league.leagueShortcut))

  def getAllSeasons: List[Season] = getAllOpenligaDbLeagues

  def getAllSports: List[Sport] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetAvailSports xmlns=\"http://msiggi.de/Sportsdata/Webservices\" />\n  </soap:Body>\n</soap:Envelope>"
    soap[List[OpenligaDbSoapSport]](xmlString)
  }

  private def getAllOpenligaDbLeaguesBySport(sportId: Long): List[OpenligaDbSoapLeague] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetAvailLeaguesBySports xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <sportID>%s</sportID>\n    </GetAvailLeaguesBySports>\n  </soap:Body>\n</soap:Envelope>".format(sportId)
    soap[List[OpenligaDbSoapLeague]](xmlString)
  }

  def getAvailLeaguesBySportId(sportId: Long): List[League] = {
    val resultMap: mutable.Map[String, League] = mutable.Map()
    getAllOpenligaDbLeaguesBySport(sportId).foreach(openLeague => {
      resultMap.put(openLeague.leagueShortcut, openLeague)
    })
    resultMap.values.toList
  }

  def getAvailSeasonsBySportId(sportId: Long): List[Season] = getAllOpenligaDbLeaguesBySport(sportId)

  def getLastMatchForLeague(shortName: String): Option[Match] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetLastMatch xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n    </GetLastMatch>\n  </soap:Body>\n</soap:Envelope>".format(shortName)
    val soapMatch = soap[OpenligaDbSoapMatchHelper](xmlString)
    OpenligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getNextMatchForLeague(shortName: String): Option[Match] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetNextMatch xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n    </GetNextMatch>\n  </soap:Body>\n</soap:Envelope>".format(shortName)
    val soapMatch = soap[OpenligaDbSoapMatchHelper](xmlString)
    OpenligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getLastMatchForSeasonAndTeam(seasonId: Long, teamId: Long): Option[Match] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetNextMatchByLeagueTeam xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueId>%s</leagueId>\n      <teamId>%s</teamId>\n    </GetNextMatchByLeagueTeam>\n  </soap:Body>\n</soap:Envelope>".format(seasonId, teamId)
    val soapMatch = soap[OpenligaDbSoapMatchHelper](xmlString)
    OpenligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getNextMatchForSeasonAndTeam(seasonId: Long, teamId: Long): Option[Match] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetLastMatchByLeagueTeam xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueId>%s</leagueId>\n      <teamId>%s</teamId>\n    </GetLastMatchByLeagueTeam>\n  </soap:Body>\n</soap:Envelope>".format(seasonId, teamId)
    val soapMatch = soap[OpenligaDbSoapMatchHelper](xmlString)
    OpenligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getLastChangeDateForSeason(leagueShortcode: String, seasonYear: Long): DateTime = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetLastChangeDateByLeagueSaison xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n      <leagueSaison>%s</leagueSaison>\n    </GetLastChangeDateByLeagueSaison>\n  </soap:Body>\n</soap:Envelope>".format(leagueShortcode, seasonYear)
    soap[DateTime](xmlString)
  }

  // Todo: rewrite for better performance without manual request all manuall
  def getMatchesBetween(leagueShortcode: String, from: DateTime, to: DateTime): List[Match] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetMatchdataByLeagueDateTime xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <fromDateTime>%s</fromDateTime>\n      <toDateTime>%s</toDateTime>\n      <leagueShortcut>%s</leagueShortcut>\n    </GetMatchdataByLeagueDateTime>\n  </soap:Body>\n</soap:Envelope>".format(from, to, leagueShortcode)
    val soapHelperList = soap[List[OpenligaDbSoapMatchHelper]](xmlString)
    val resultList: ArrayBuffer[Match] = ArrayBuffer()
    soapHelperList.foreach(soapMatch => resultList.+=(OpenligaDbService.getMatchdataById(soapMatch.matchID.toLong).get))
    resultList.toList
  }

}

