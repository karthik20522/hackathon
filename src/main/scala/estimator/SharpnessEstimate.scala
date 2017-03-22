package com.getty.hackathon

import org.openimaj.image.FImage
import org.openimaj.image.feature.global._

object SharpnessEstimate {
  def getValue(fImage: FImage): Double = {
    val sharpDetector = new Sharpness()
    sharpDetector.analyseImage(fImage)
    sharpDetector.getSharpness
  }

  def getValueVariation(fImage: FImage): Double = {
    val sharpnessVariation = new SharpnessVariation()
    sharpnessVariation.analyseImage(fImage)
    sharpnessVariation.getSharpnessVariation()
  }

  def getValueSharpPixel(fImage: FImage): Double = {
    val sharpnessVariation = new SharpPixelProportion()
    sharpnessVariation.analyseImage(fImage)
    sharpnessVariation. getBlurredPixelProportion()
  }
}