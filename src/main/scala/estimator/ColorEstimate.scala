package com.getty.hackathon

import org.openimaj.image.MBFImage
import org.openimaj.image.feature.global.Colorfulness

object ColorEstimate {
  def getValue(mbfImage: MBFImage): Double = {
    val colorFulDetector = new Colorfulness()
    mbfImage.analyseWith(colorFulDetector)
    colorFulDetector.getColorfulness
  }
}