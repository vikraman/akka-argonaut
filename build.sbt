name := "akka-argonaut"

organization := "org.vikraman"

description := "Argonaut json marshalling for akka streams"

homepage := Some(url(s"https://github.com/vikraman/${name.value}"))

licenses += "MIT" -> url(s"https://github.com/vikraman/${name.value}/blob/${version.value}/LICENSE")

scalaVersion in ThisBuild := "2.12.0"

scalacOptions in ThisBuild := Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xlint"
)

fork := true

crossScalaVersions := Seq("2.11.8", "2.12.0")

libraryDependencies ++= {
  val akkaV       = "2.4.13"
  val akkaHttpV   = "10.0.0-RC2"
  val argonautV   = "6.2-RC2"
  val scalaTestV  = "3.0.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"              % akkaV,
    "com.typesafe.akka" %% "akka-stream"             % akkaV,
    "com.typesafe.akka" %% "akka-http"               % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit"       % akkaHttpV % Test,
    "io.argonaut"       %% "argonaut"                % argonautV,
    "org.scalatest"     %% "scalatest"               % scalaTestV % Test
  )
}

useGpg := true
