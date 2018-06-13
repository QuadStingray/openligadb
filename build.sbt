
name := "openligadb"

organization := "com.quadstingray"

scalaVersion := "2.12.6"

homepage := Some(url("https://github.com/QuadStingray/sbt-javafx"))

scmInfo := Some(ScmInfo(url("https://github.com/QuadStingray/openligadb"), "https://github.com/QuadStingray/openligadb.git"))

developers := List(Developer("QuadStingray", "QuadStingray", "github@quadstingray.com", url("https://github.com/QuadStingray")))

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

// Tests
libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "4.2.0" % "test",
  "junit" % "junit" % "4.12" % "test"
)

// akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.2",
  "com.typesafe.akka" %% "akka-stream" % "2.5.13"
)

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.5.4",
  "com.google.inject" % "guice" % "4.2.0",
  "joda-time" % "joda-time" % "2.10"
)

// Caching
libraryDependencies ++= Seq(
  "org.cache2k" % "cache2k-api" % "1.0.2.Final",
  "org.cache2k" % "cache2k-core" % "1.0.2.Final"
)

// Logging
libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
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