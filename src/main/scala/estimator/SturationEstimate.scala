package com.getty.hackathon

import org.openimaj.image.MBFImage
import org.openimaj.image.feature.global._

object SturationEstimate {
  def getValue(mbfImage: MBFImage): Double = {
    val saturation = new Saturation()
    saturation.analyseImage(mbfImage)
    saturation.getSaturation()
  }

  def getValueVariation(mbfImage: MBFImage): Double = {
    val saturation = new SaturationVariation()
    saturation.analyseImage(mbfImage)
    saturation.getSaturationVariation()
  }
}