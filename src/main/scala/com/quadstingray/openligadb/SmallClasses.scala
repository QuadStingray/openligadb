package com.quadstingray.openligadb

case class Goal(id: Long, player: Player, matchMinute: Int, scoreTeam1: Int, scoreTeam2: Int, isOvertime: Boolean, isOwnGoal: Boolean, isPenalty: Boolean)

case class GoalGetter(player: Player, goalCount: Int)

case class Location(id: Long, stadium: String, city: String)

case class MatchResult(id: Long, name: String, scoreTeam1: Int, scoreTeam2: Int, resultOrderID: Int, typeId: Int, description: String)

case class Player(id: Long, name: String, nationality: Option[String] = None)