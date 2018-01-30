name := "openligadb"

organization := "com.quadstingray"

version := "0.0.2-SNAPSHOT"

scalaVersion := "2.12.4"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

// Tests
libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "4.0.1" % "test",
  "junit" % "junit" % "4.12" % "test"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "org.json4s" %% "json4s-native" % "3.5.3",
  "joda-time" % "joda-time" % "2.9.9"
)

libraryDependencies += "org.cache2k" % "cache2k-api" % "1.0.1.Final"

libraryDependencies += "org.cache2k" % "cache2k-core" % "1.0.1.Final"

resolvers += Resolver.jcenterRepo

publishTo := {
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository/")))
  else
    Some("Bintray API Realm" at "https://api.bintray.com/content/quadstingray/maven/%s/%s/publish".format(name.value, version.value))
}


credentials += Credentials(new File("credentials.properties"))