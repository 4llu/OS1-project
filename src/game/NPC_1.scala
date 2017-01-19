package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 17/01/2017.
  */
class NPC_1(x:Int, y:Int, world:World) extends Monster(x:Int, y:Int, world:World, 0.1, 10, 32) {
  var weapon: Weapon = new FireballSpell(); // FIXME Wrong weapon
  
  this.loadSprites()
  var sprite = spritesByDirection.get(South).get(1)
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  var direction = this.playerDirection()
  
  val deathAnimationLength = (1.0 * 1000).toInt
  
  val baseSpriteChangeCooldown = 0.1
      
  def loadSprites() = {
    val goblinSprites = ImageIO.read(new File("media/goblin1Walk.png"))
    val directions = Vector[Direction](
         South, SouthWest, West, NorthWest, North, NorthEast, East, SouthEast)
    for (y <- 0 to 7) {
      val sprites = ArrayBuffer[BufferedImage]()
      for (x <- 0 until 720 by 120) {
        sprites += goblinSprites.getSubimage(x, y*165, 120, 165)
      }
      spritesByDirection += directions(y) -> sprites.toVector
    }
  }
}
