package com.quadstingray.openligadb

case class GoalGetter(player: Player, goalCount: Int)

case class Location(id: Long, stadium: String, city: String)

case class Player(id: Long, name: String, nationality: Option[String] = None)