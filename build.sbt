name := "akka-argonaut"

organization := "org.vikraman"

description := "Argonaut json marshalling for akka streams"

homepage := Some(url(s"https://github.com/vikraman/${name.value}"))

licenses += "MIT" -> url(
  s"https://github.com/vikraman/${name.value}/blob/${version.value}/LICENSE"
)

scalaVersion in ThisBuild := "2.13.1"

scalacOptions in ThisBuild := Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xlint"
)

fork := true

crossScalaVersions := Seq("2.12.10", "2.13.1")

libraryDependencies ++= {
  val akkaV = "2.6.1"
  val akkaHttpV = "10.1.11"
  val argonautV = "6.2.3"
  val scalaTestV = "3.1.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % Test,
    "io.argonaut" %% "argonaut" % argonautV,
    "org.scalatest" %% "scalatest" % scalaTestV % Test
  )
}
