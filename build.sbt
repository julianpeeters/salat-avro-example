name := "salat-avro-example"

version := "0.0.1-SNAPSHOT"

organization := "com.julianpeeters"

scalaVersion := "2.9.1"

resolvers ++= Seq(
  "repo.scalatools rels" at "https://oss.sonatype.org/content/groups/scala-tools/",
  "repo.scalatools snaps" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq( 
  "org.scalatest" %% "scalatest" % "1.8" % "test", 
  "org.apache.avro" % "avro" % "1.5.1",
  "com.novus" %% "salat-core" % "0.0.8"
)
