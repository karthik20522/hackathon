package com.getty.hackathon

import org.openimaj.image.feature.global.Naturalness
import org.openimaj.image.MBFImage

object NaturalnessEstimate {
  val naturalEstimator = new Naturalness()
  
  def getValue(mbfImage: MBFImage): Double = {
    naturalEstimator.analyseImage(mbfImage)
    naturalEstimator.getNaturalness
  }
}