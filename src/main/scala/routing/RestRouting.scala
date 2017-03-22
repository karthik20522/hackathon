package com.getty.hackathon

import akka.actor._
import spray.http._
import spray.routing._
import scala.util.{ Try, Success, Failure }
import akka.util.Timeout
import scala.concurrent.duration._
import com.github.nscala_time.time.Imports.DateTime

class RestRouting extends HttpService with Actor with CalculateService {

  implicit def actorRefFactory = context
  implicit val timeout = Timeout(10 seconds)

  def receive = runRoute(calculateRoute)
}

trait CalculateService extends HttpService with JsonSupport { actor: Actor =>

  import context.dispatcher

  val analyseRoute = path("analyse" / Rest) & get

  def calculateRoute = analyseRoute { (rest) =>
    {
      val calculateEstimate = new Calculate()
      
      onComplete(calculateEstimate.getEstimate(rest)) {
        case Success(estimate: Estimate) => complete(StatusCodes.OK, estimate)
        case Failure(ex) => complete(StatusCodes.InternalServerError, ex.getMessage)
      }
    }
  }
}