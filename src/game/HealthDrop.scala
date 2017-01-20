package game

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class HealthDrop(x: Int, y: Int, world: World) extends Drop(x, y, world) {
  
  val healSize = 20
  var sprite: BufferedImage = ImageIO.read(new File("media/hp_potion.png"))
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  
  val collidesWithMonsters = false
  val collidesWithPlayer = false
  
  override def pickedUp(): Unit = {
    super.pickedUp()
    game.player.heal(this.healSize)
  }
}