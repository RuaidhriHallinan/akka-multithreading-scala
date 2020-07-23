name := "akka-multithreading-scala"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.7"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % akkaVersion

libraryDependencies +=
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0"

libraryDependencies +=
  "org.scalatest" %% "scalatest" % "3.2.0" % Test
