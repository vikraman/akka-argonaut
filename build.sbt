name := "akka-argonaut"

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
  val akkaV       = "2.3.9"
  val akkaStreamV = "1.0-M5"
  val argonautV   = "6.1-M6"
  val scalaTestV  = "2.2.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                        % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"       % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"            % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"    % akkaStreamV,
    "io.argonaut"       %% "argonaut"                          % argonautV,
    "org.scalatest"     %% "scalatest"                         % scalaTestV % "test"
  )
}
