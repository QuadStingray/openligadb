# First Steps

Add to `build.sbt` the following content:

@@@ vars
```sbt
libraryDependencies += "com.quadstingray" % "openligadb" % "$project.version$"
```
@@@


The first thing you need is the Id for the league you are interested in. How you can find the id? @ref:[Take a look here.](league/find-league.md). We take `bl1` (German Bundesliga) for our sample.


So lets make some basic request!

## Get the Current Season
@@snip [Get Current Season](../../test/scala/com/quadstingray/openligadb/LeagueSpec.scala) { #first-requests-currentseason }

## Get the specific Season
@@snip [Get Current Season](../../test/scala/com/quadstingray/openligadb/LeagueSpec.scala) { #first-requests-currentseason }


## Get the current Matches
@@@ note
The current day is increased in each case half the time between the last game of the last match day and the first game of the next match day.
@@@

@@snip [Get Current Matches](../../test/scala/com/quadstingray/openligadb/LeagueSpec.scala) { #first-requests-currentMatches }

## Get next Matches
@@snip [Get Current Season](../../test/scala/com/quadstingray/openligadb/LeagueSpec.scala) { #first-requests-nextmatch }

@@@ index

 - [League](league/find-league.md)

@@@
