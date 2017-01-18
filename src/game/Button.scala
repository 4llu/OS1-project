package game

import java.awt.image.BufferedImage

class Button(x: Int, y: Int, var defaultSprite:BufferedImage, var hoverSprite:BufferedImage) extends C_Drawable {
  var sprite = defaultSprite
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, null)
  var hover = false
  var clicked = false
  def hoverTrue() = {
    this.hover = true
    this.sprite = hoverSprite
  } 
  def hoverFalse() = {
    this.hover = false
    this.sprite = defaultSprite
  }
}