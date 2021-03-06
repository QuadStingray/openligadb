
name := "openligadb"

organization := "com.quadstingray"

scalaVersion := "2.12.8"

homepage := Some(url("https://quadstingray.github.io/openligadb/"))

scmInfo := Some(ScmInfo(url("https://github.com/QuadStingray/openligadb"), "https://github.com/QuadStingray/openligadb.git"))

developers := List(Developer("QuadStingray", "QuadStingray", "github@quadstingray.com", url("https://github.com/QuadStingray")))

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

// Tests
libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "4.4.1" % "test",
  "junit" % "junit" % "4.12" % "test"
)

// akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.7",
  "com.typesafe.akka" %% "akka-stream" % "2.5.21"
)

val json4sVersion = "3.6.5"
libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % json4sVersion,
  "org.json4s" %% "json4s-xml" % json4sVersion,
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
)

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.2.2",
  "joda-time" % "joda-time" % "2.10.1"
)

// Caching
libraryDependencies += "com.github.blemale" %% "scaffeine" % "2.5.0"

// Logging
libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

resolvers += Resolver.jcenterRepo

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.jcenterRepo

bintrayReleaseOnPublish in ThisBuild := true

import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)