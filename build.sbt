name := "akka-argonaut"
version := "0.2.0"
organization := "org.vikraman"
description := "Argonaut json marshalling for akka streams"
homepage := Some(url(s"https://github.com/vikraman/${name.value}"))
licenses += "MIT" -> url(s"https://github.com/vikraman/${name.value}/blob/${version.value}/LICENSE")

scalaVersion in ThisBuild := "2.11.6"

scalacOptions in ThisBuild := Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xlint",
  "-optimise"
)

libraryDependencies ++= {
  val akkaV       = "2.3.11"
  val akkaStreamV = "1.0-RC4"
  val argonautV   = "6.1-M6"
  val scalaTestV  = "2.2.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                      % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"        % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"     % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"  % akkaStreamV,
    "io.argonaut"       %% "argonaut"                        % argonautV,
    "org.scalatest"     %% "scalatest"                       % scalaTestV % "test"
  )
}

bintraySettings
useGpg := true
