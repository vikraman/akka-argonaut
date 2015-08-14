name := "akka-argonaut"

organization := "org.vikraman"

description := "Argonaut json marshalling for akka streams"

homepage := Some(url(s"https://github.com/vikraman/${name.value}"))

licenses += "MIT" -> url(s"https://github.com/vikraman/${name.value}/blob/${version.value}/LICENSE")

scalaVersion in ThisBuild := "2.11.7"

scalacOptions in ThisBuild := Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xlint",
  "-optimise"
)

libraryDependencies ++= {
  val akkaV       = "2.3.12"
  val akkaStreamV = "1.0"
  val argonautV   = "6.1"
  val scalaTestV  = "2.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                      % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"        % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"  % akkaStreamV % Test,
    "io.argonaut"       %% "argonaut"                        % argonautV,
    "org.scalatest"     %% "scalatest"                       % scalaTestV % Test
  )
}

bintraySettings
useGpg := true
