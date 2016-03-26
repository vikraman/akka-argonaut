name := "akka-argonaut"

organization := "org.vikraman"

description := "Argonaut json marshalling for akka streams"

homepage := Some(url(s"https://github.com/vikraman/${name.value}"))

licenses += "MIT" -> url(s"https://github.com/vikraman/${name.value}/blob/${version.value}/LICENSE")

scalaVersion in ThisBuild := "2.11.8"

scalacOptions in ThisBuild := Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xlint",
  "-optimise"
)

fork := true

libraryDependencies ++= {
  val akkaV       = "2.4.2"
  val argonautV   = "6.1"
  val scalaTestV  = "2.2.6"
  Seq(
    "com.typesafe.akka" %% "akka-actor"              % akkaV,
    "com.typesafe.akka" %% "akka-stream"             % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental"  % akkaV,
    "com.typesafe.akka" %% "akka-http-testkit"       % akkaV % Test,
    "io.argonaut"       %% "argonaut"                % argonautV,
    "org.scalatest"     %% "scalatest"               % scalaTestV % Test
  )
}

useGpg := true
