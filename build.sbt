name := "devoxx-graphql-backend"
version := "0.1.0-SNAPSHOT"

description := "A GraphQL server written with akka-http and sangria."

scalaVersion := "2.12.0"
scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.0.0-RC4",
  "org.sangria-graphql" %% "sangria-spray-json" % "0.3.2",
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
  "ch.megard" %% "akka-http-cors" % "0.1.9",
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

Revolver.settings
enablePlugins(JavaAppPackaging)
