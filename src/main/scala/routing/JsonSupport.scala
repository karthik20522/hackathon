package com.getty.hackathon

import spray.httpx.Json4sSupport
import org.json4s._
import org.json4s.{ Formats, DefaultFormats }

trait JsonSupport extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}