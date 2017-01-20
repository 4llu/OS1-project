package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 11/11/2016.
  */
trait C_Drawable extends C_Locatable {
  var sprite: BufferedImage
  var remove = false

  def centerX: Int = this.location.x + this.sprite.getWidth()/2
  def centerY: Int = this.location.y + this.sprite.getHeight()/2

  // TODO Sprite list for different actions
}
