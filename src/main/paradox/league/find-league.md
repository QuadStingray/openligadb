# How to find your league

## Via API
The API Call returns an List of League

@@snip [Get Current Season](../../../test/scala/com/quadstingray/openligadb/OpenligaServiceSpec.scala) { #find-league }

## Via Webinterface
* Check [OpenligaDB Website](https://www.openligadb.de/Datenhaushalt/)
* Find your sport and take `Liga-Shortcut` as shortName to init `League(shortName)`
