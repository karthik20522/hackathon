package com.getty.hackathon

import scalaj.http._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent._
import akka.actor._
import java.io.ByteArrayInputStream
import org.openimaj.image._
import org.openimaj.image.processing.resize.ResizeProcessor
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ read, write }
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s._

case class Estimate(
  blur: Double,
  avgBrightness: Double,
  color: Double,
  contrast: Double,
  contrastRMS: Double,
  contrastWeber: Double,
  natural: Double,
  sharpness: Double,
  sharnessVariation: Double,
  // sharpnessPixel: Double,
  saturation: Double,
  saturationVariation: Double)

class Calculate(implicit val actorRefFactory: ActorRefFactory) extends JsonSupport {
  import actorRefFactory.dispatcher

  def getEstimate(uri: String): Future[Estimate] = {

    val estimateValues: Future[Estimate] = for {
      responseBytes: HttpResponse[Array[Byte]] <- Future { Http(uri).asBytes }
      inputStream: ByteArrayInputStream <- Future { new ByteArrayInputStream(responseBytes.body) }
      originalImage: MBFImage <- Future { ImageUtilities.readMBF(inputStream) }
      resizedImage: MBFImage <- Future { resizeImage(originalImage) }
      grayImage: FImage <- Future { resizedImage.flatten() }

      blurValue: Double <- Future { BlurEstimate.getValue(grayImage) }
      avgBrightness: Double <- Future { BrightnessEstimate.getValue(resizedImage) }
      colorValue: Double <- Future { ColorEstimate.getValue(resizedImage) }

      contrastValue: Double <- Future { ContrastEstimate.getValue(resizedImage) }
      contrastValueRMS: Double <- Future { ContrastEstimate.getValueRMS(grayImage) }
      contrastValueWeber: Double <- Future { ContrastEstimate.getValueWeber(grayImage) }

      naturalValue: Double <- Future { NaturalnessEstimate.getValue(resizedImage) }

      sharpnessValue: Double <- Future { SharpnessEstimate.getValue(grayImage) }
      sharpnessValueVariation: Double <- Future { SharpnessEstimate.getValueVariation(grayImage) }
      //sharpnessValuePixel: Double <- Future { SharpnessEstimate.getValueSharpPixel(grayImage) }

      saturationValue: Double <- Future { SturationEstimate.getValue(resizedImage) }
      saturationValueVariation: Double <- Future { SturationEstimate.getValueVariation(resizedImage) }
      //faceValue: Double <- Future { getFaces(responseBytes.body) }
    } yield {
      try {
        if (inputStream != null)
          inputStream.close()
      } catch {
        case _ => ;
      }

      Estimate(blurValue,
        avgBrightness,
        colorValue,
        contrastValue,
        contrastValueRMS,
        contrastValueWeber,
        naturalValue,
        sharpnessValue,
        sharpnessValueVariation,
        // sharpnessValuePixel,
        saturationValue,
        saturationValueVariation)
    }

    estimateValues
  }

  private def getFaces(bytes: Array[Byte]): Double = {
    val response = Http("https://westus.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false").
      postData(bytes).
      header("Ocp-Apim-Subscription-Key", "629e87580be94f2799aaf5b3a6a76d50").
      header("content-type", "application/octet-stream").
      asString

    println(response.body)

    val numberOfFases = (parse(response.body) \ "faceId").extract[List[String]]
    numberOfFases.size
  }

  private def resizeImage(originalImage: MBFImage): MBFImage = {
    (originalImage.getHeight(), originalImage.getWidth()) match {
      case (h @ _, w @ _) if h > 640 || w > 640 => originalImage.process(new ResizeProcessor(594))
      case (_, _) => originalImage
    }
  }
}