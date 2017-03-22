package com.getty.hackathon

import org.openimaj.image.FImage
import org.openimaj.image.processing.algorithm.FourierTransform

object BlurEstimate {
  def getValue(fImage: FImage): Double = {
    val threshold: Float = 2f;
    val ft = new FourierTransform(fImage, false)
    val mag = ft.getMagnitude()

    var count = 0;
    for (y <- 0 to mag.height - 1) {
      for (x <- 0 to mag.width - 1) {
        if (Math.abs(mag.pixels(y)(x)) > threshold) {
          count = count + 1
        }
      }
    }
    val bpp: Double = count.doubleValue / (mag.height * mag.width).doubleValue
    bpp
  }
}