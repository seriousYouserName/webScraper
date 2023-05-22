name := """immoscraper_play_2.0"""
organization := "immoscraper"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaJdbc,
  "org.xerial" % "sqlite-jdbc" % "3.8.6"
)