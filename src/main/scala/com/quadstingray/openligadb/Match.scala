package com.quadstingray.openligadb

import com.quadstingray.openligadb.exceptions.NoMatchFoundException
import com.quadstingray.openligadb.services.OpenligaDbService
import org.joda.time.DateTime

case class Match(id: Long, team1: Team, team2: Team, goals: List[Goal],
                 finalResult: Option[MatchResult], matchResults: List[MatchResult], matchGroup: MatchGroup,
                 location: Option[Location], numberOfViewers: Option[Int], isFinished: Boolean, matchDateTime: DateTime,
                 lastUpdateDateTime: DateTime
                ) {

}


object Match {
  def apply(id: Long): Match = {
    try {
      OpenligaDbService.getMatchdataById(id).get
    } catch {
      case e: java.util.NoSuchElementException =>
        throw new NoMatchFoundException()
    }
  }
}