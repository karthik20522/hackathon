name := "Hackathon2017"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.9"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(  
  "openImaJ repo" at "http://maven.openimaj.org",
  "gphat" at "https://raw.github.com/gphat/mvn-repo/master/releases/"
)

libraryDependencies ++= {
  val akkaV = "2.4.7"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"                %%    "spray-can"           %   sprayV,
    "io.spray"                %%    "spray-routing"       %   sprayV,
    "com.typesafe.akka"       %     "akka-actor_2.11"     %   akkaV,
    "org.json4s"              %     "json4s-native_2.11"  %   "3.3.0",
    "com.github.nscala-time"  %%     "nscala-time"        %   "2.10.0",
 	  "org.openimaj" 			      %     "image-processing" 	  %   "1.3.5",
    "org.openimaj" 			      %     "faces"               %	  "1.3.5",
    "org.openimaj.tools" 	    %     "core-tool"           %   "1.3.5",
    "org.scalaj"			        %     "scalaj-http_2.11"    %   "2.3.0",
    "com.typesafe"            %     "config"              %   "1.3.0",
    "org.scalatest"           %     "scalatest_2.11"      %   "2.2.6" % "test",
    "com.typesafe.akka"       %%    "akka-slf4j"          %   akkaV,
    "ch.qos.logback"          %     "logback-classic"     %   "1.1.3",
    "com.typesafe.scala-logging" %% "scala-logging"       %   "3.1.0"
  )
}

// Assembly settings
mainClass in Global := Some("com.getty.hackathon")