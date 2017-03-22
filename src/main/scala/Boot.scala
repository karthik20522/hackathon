package com.getty.hackathon

import akka.io.IO
import spray.can.Http
import akka.actor.{ Props, ActorSystem }
import akka.actor._

object Boot extends App {
  implicit val system = ActorSystem("Hackathon2017")
  //AkkaSampling.print(system)

  val serviceActor = system.actorOf(Props(new RestRouting), name = "rest-routing")

  system.registerOnTermination {
    system.log.info("Hackathon shutdown.")
  }

  IO(Http) ! Http.Bind(serviceActor, "0.0.0.0", port = 8089)
}