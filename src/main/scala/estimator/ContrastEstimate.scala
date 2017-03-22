package com.getty.hackathon

import org.openimaj.image.feature.global._
import org.openimaj.image._

object ContrastEstimate {
  def getValue(mbfImage: MBFImage): Double = {
    val contrastEstimator = new RGBRMSContrast()
    contrastEstimator.analyseImage(mbfImage)
    contrastEstimator.getContrast()
  }

  def getValueRMS(fImage: FImage): Double = {
    val contrastEstimator = new RMSContrast()
    contrastEstimator.analyseImage(fImage)
    contrastEstimator.getContrast()
  }

   def getValueWeber(fImage: FImage): Double = {
    val contrastEstimator = new WeberContrast()
    contrastEstimator.analyseImage(fImage)
    contrastEstimator.getContrast()
  }
}