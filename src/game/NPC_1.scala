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
  var sprite: BufferedImage = ImageIO.read(new File("media/player.png")) // FIXME Wrong sprite
  
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  var direction = this.playerDirection()
}
