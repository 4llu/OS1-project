package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.collection.mutable.ArrayBuffer

class Penguin3(x:Int, y:Int, world:World) extends Monster(x:Int, y:Int, world:World, 0.1, 20, 50) {
  var weapon: Spell = new MeleeSpell(10)
  
  this.loadSprites()
  var sprite = spritesByDirection.get(South).get(1)
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  var direction = this.getDirection(game.player)
  
  val deathAnimationLength = 0
  
  val baseSpriteChangeCooldown = 0.1
      
  def loadSprites() = {
    val penguinSprites = ImageIO.read(new File("media/penguin.png"))
    val directions = Vector[Direction](
         South, SouthEast, East, NorthEast, North, NorthWest, West, SouthWest)
    for (y <- 0 to 7) {
      val sprites = ArrayBuffer[BufferedImage]()
      for (x <- 0 until 164 by 41) {
        sprites += penguinSprites.getSubimage(x, y*42, 41, 42)
      }
      spritesByDirection += directions(y) -> sprites.toVector
    }
  }
}