package com.getty.hackathon

import org.openimaj.image.MBFImage
import org.openimaj.image.feature.global._

object BrightnessEstimate {
  def getValue(mbfImage: MBFImage): Double = {
    val avgBrightness = new AvgBrightness()
    mbfImage.analyseWith(avgBrightness)
    avgBrightness.getBrightness
  }
}