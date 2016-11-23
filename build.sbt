name := "hackathon-graphql-backend"
version := "0.1.0-SNAPSHOT"

description := "A GraphQL server written with akka-http and sangria."

scalaVersion := "2.12.0"
scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.0.0-RC4",
  "org.sangria-graphql" %% "sangria-spray-json" % "0.3.2",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.11",
  "ch.megard" %% "akka-http-cors" % "0.1.8",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

Revolver.settings
enablePlugins(JavaAppPackaging)
