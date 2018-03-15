package com.quadstingray.openligadb.services

private case class OpenligaDbTeam(TeamId: Long, TeamName: String, ShortName: String, TeamIconUrl: String)

private case class OpenligaDbGroup(GroupID: Long, GroupName: String, GroupOrderID: Int)

private case class OpenligaDbMatch(MatchID: Long, Group: OpenligaDbGroup, Team1: OpenligaDbTeam, Team2: OpenligaDbTeam, MatchResults: List[OpenligaDbMatchResult], Goals: List[OpenligaDbGoal], Location: Option[OpenligaDbLocation], NumberOfViewers: Option[Int], MatchIsFinished: Boolean, LastUpdateDateTime: String, MatchDateTime: String, TimeZoneID: String, LeagueId: Long, LeagueName: String, MatchDateTimeUTC: String)

private case class OpenligaDbMatchResult(ResultID: Long, ResultName: String, PointsTeam1: Int, PointsTeam2: Int, ResultOrderID: Int, ResultTypeID: Int, ResultDescription: String)

private case class OpenligaDbLocation(LocationID: Long, LocationStadium: String, LocationCity: String)

private case class OpenligaDbGoal(GoalID: Long, ScoreTeam1: Int, ScoreTeam2: Int, MatchMinute: Int = -1, GoalGetterID: Long, GoalGetterName: String, IsPenalty: Boolean, IsOwnGoal: Boolean, IsOvertime: Boolean, Comment: String)

private case class OpenligaDbGoalGetter(GoalCount: Int, GoalGetterId: Long, GoalGetterName: String, GoalGetterNationality: Option[String])

private case class OpenligaDbTableItem(TeamInfoId: Int, TeamName: String, ShortName: String, TeamIconUrl: String, Points: Int, OpponentGoals: Int, Goals: Int, Matches: Int, Won: Int, Lost: Int, Draw: Int, GoalDiff: Int)
