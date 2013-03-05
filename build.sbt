name := "salat-avro-example"

version := "0.0.1-SNAPSHOT"

organization := "com.julianpeeters"

//scalaVersion := "2.8.1"
scalaVersion := "2.9.1"

resolvers += "repo.scalatools rels" at "https://oss.sonatype.org/content/groups/scala-tools/"

resolvers += "repo.scalatools snaps" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "repo.novus rels" at "http://repo.novus.com/releases/"

resolvers += "repo.novus snaps" at "http://repo.novus.com/snapshots/"

resolvers += Resolver.file("Local Ivy Repository", file("/home/julianpeeters/.ivy2/local/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq( 
  "org.scalatest" %% "scalatest" % "1.8" % "test",
  "org.ow2.asm" % "asm-util" % "4.1", 
  "org.apache.avro" % "avro" % "1.7.1",
  "com.banno.salat.avro" %% "salat-avro" % "0.0.8-archive",
  "com.novus" %% "salat-util" % "1.9.1"
 // "ch.qos.logback" % "logback-core" % "1.0.0",   //for use with scalaVersion 2.8.1
 // "ch.qos.logback" % "logback-classic" % "1.0.0" //for use with scalaVersion 2.8.1
)
