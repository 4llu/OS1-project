package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * Created by Allu on 18/01/2017.
  */
class Firebomb(x:Int, y:Int, world:World, var direction: Direction, hitsMonsters: Boolean, hitsPlayer: Boolean) 
  extends Projectile(x, y, world, hitsMonsters, hitsPlayer) {
  var speed = 1.1f
  val damage = 50
  var sprite: BufferedImage = ImageIO.read(new File("media/firebomb.png"))
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
}
