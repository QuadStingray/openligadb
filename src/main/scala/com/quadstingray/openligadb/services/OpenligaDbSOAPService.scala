package com.quadstingray.openligadb.services

import javax.inject.Inject

import com.quadstingray.openligadb._
import org.joda.time.DateTime
import org.json4s.DefaultFormats

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait OpenligaDbSOAPService {

  def getAvailLeagues: List[League]

  def getAllSeasons(shortName: String): List[Season]

  def getAllSeasons: List[Season]

  def getAllSports: List[Sport]

  def getAvailLeaguesBySportId(sportId: Long): List[League]

  def getAvailSeasonsBySportId(sportId: Long): List[Season]

  def getLastMatchForLeague(shortName: String): Option[MatchData]

  def getGoalsForLeagueSeason(shortName: String, leagueId: Int): List[Goal]

  def getNextMatchForLeague(shortName: String): Option[MatchData]

  def getLastMatchForSeasonAndTeam(seasonId: Long, teamId: Long): Option[MatchData]

  def getNextMatchForSeasonAndTeam(seasonId: Long, teamId: Long): Option[MatchData]

  def getLastChangeDateForSeason(leagueShortcode: String, seasonYear: Long): DateTime

  def getLastChangeDateForMatchGroup(leagueShortcode: String, seasonYear: Long, orderId: Long): DateTime

  def getMatchesBetween(leagueShortcode: String, from: DateTime, to: DateTime): List[MatchData]

}

private[openligadb] class OpenligaDbSOAPServiceImplementation @Inject()(httpService: HttpService, openligaDbService: OpenligaDbService) extends OpenligaDbSOAPService with OpenligaDbImplicits {

  implicit val defaultFormats: DefaultFormats.type = DefaultFormats

  private def getAllOpenligaDbLeagues: List[OpenligaDbSoapLeague] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetAvailLeagues xmlns=\"http://msiggi.de/Sportsdata/Webservices\" />\n  </soap:Body>\n</soap:Envelope>"
    httpService.soap[List[OpenligaDbSoapLeague]](xmlString)
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
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetAvailSports xmlns=\"http://msiggi.de/Sportsdata/Webservices\" />\n  </soap:Body>\n</soap:Envelope>"
    httpService.soap[List[OpenligaDbSoapSport]](xmlString)
  }

  private def getAllOpenligaDbLeaguesBySport(sportId: Long): List[OpenligaDbSoapLeague] = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetAvailLeaguesBySports xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <sportID>%s</sportID>\n    </GetAvailLeaguesBySports>\n  </soap:Body>\n</soap:Envelope>").format(sportId)
    httpService.soap[List[OpenligaDbSoapLeague]](xmlString)
  }

  def getAvailLeaguesBySportId(sportId: Long): List[League] = {
    val resultMap: mutable.Map[String, League] = mutable.Map()
    getAllOpenligaDbLeaguesBySport(sportId).foreach(openLeague => {
      resultMap.put(openLeague.leagueShortcut, openLeague)
    })
    resultMap.values.toList
  }

  def getAvailSeasonsBySportId(sportId: Long): List[Season] = getAllOpenligaDbLeaguesBySport(sportId)

  def getLastMatchForLeague(shortName: String): Option[MatchData] = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetLastMatch xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n    </GetLastMatch>\n  </soap:Body>\n</soap:Envelope>").format(shortName)
    val soapMatch = httpService.soap[OpenligaDbSoapMatchHelper](xmlString)
    openligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getGoalsForLeagueSeason(shortName: String, leagueId: Int): List[Goal] = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetGoalsByLeagueSaison xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n      <leagueSaison>%s</leagueSaison>\n    </GetGoalsByLeagueSaison>\n  </soap:Body>\n</soap:Envelope>").format(shortName, leagueId)
    val soapMatch = httpService.soap[List[OpenligaDbSoapGoal]](xmlString)
    soapMatch
  }

  def getNextMatchForLeague(shortName: String): Option[MatchData] = {
    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    <GetNextMatch xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n    </GetNextMatch>\n  </soap:Body>\n</soap:Envelope>".format(shortName)
    val soapMatch = httpService.soap[OpenligaDbSoapMatchHelper](xmlString)
    openligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getLastMatchForSeasonAndTeam(seasonId: Long, teamId: Long): Option[MatchData] = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetNextMatchByLeagueTeam xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueId>%s</leagueId>\n      <teamId>%s</teamId>\n    </GetNextMatchByLeagueTeam>\n  </soap:Body>\n</soap:Envelope>").format(seasonId, teamId)
    val soapMatch = httpService.soap[OpenligaDbSoapMatchHelper](xmlString)
    openligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getNextMatchForSeasonAndTeam(seasonId: Long, teamId: Long): Option[MatchData] = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetLastMatchByLeagueTeam xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueId>%s</leagueId>\n      <teamId>%s</teamId>\n    </GetLastMatchByLeagueTeam>\n  </soap:Body>\n</soap:Envelope>").format(seasonId, teamId)
    val soapMatch = httpService.soap[OpenligaDbSoapMatchHelper](xmlString)
    openligaDbService.getMatchdataById(soapMatch.matchID.toLong)
  }

  def getLastChangeDateForSeason(leagueShortcode: String, seasonYear: Long): DateTime = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetLastChangeDateByLeagueSaison xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <leagueShortcut>%s</leagueShortcut>\n      <leagueSaison>%s</leagueSaison>\n    </GetLastChangeDateByLeagueSaison>\n  </soap:Body> \n</soap:Envelope>")
      .format(leagueShortcode, seasonYear)
    val dateString = httpService.soap[String](xmlString)
    DateTime.parse(dateString)
  }

  def getLastChangeDateForMatchGroup(leagueShortcode: String, seasonYear: Long, orderId: Long): DateTime = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetLastChangeDateByGroupLeagueSaison xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <groupOrderID>%s</groupOrderID>\n      <leagueShortcut>%s</leagueShortcut>\n      <leagueSaison>%s</leagueSaison>\n    " +
      "</GetLastChangeDateByGroupLeagueSaison>\n  </soap:Body>\n</soap:Envelope>").format(orderId, leagueShortcode, seasonYear)
    val dateString = httpService.soap[String](xmlString)
    DateTime.parse(dateString)
  }

  // Todo: rewrite for better performance without manual request all manuall
  def getMatchesBetween(leagueShortcode: String, from: DateTime, to: DateTime): List[MatchData] = {
    val xmlString = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n  <soap:Body>\n    " +
      "<GetMatchdataByLeagueDateTime xmlns=\"http://msiggi.de/Sportsdata/Webservices\">\n      <fromDateTime>%s</fromDateTime>\n      <toDateTime>%s</toDateTime>\n      <leagueShortcut>%s</leagueShortcut>\n    </GetMatchdataByLeagueDateTime>\n  </soap:Body>\n " +
      "</soap:Envelope>").format(from, to, leagueShortcode)
    val soapHelperList = httpService.soap[List[OpenligaDbSoapMatchHelper]](xmlString)
    val resultList: ArrayBuffer[MatchData] = ArrayBuffer()
    soapHelperList.foreach(soapMatch => resultList.+=(openligaDbService.getMatchdataById(soapMatch.matchID.toLong).get))
    resultList.toList
  }

}

