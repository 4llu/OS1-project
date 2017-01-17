package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * Created by Allu on 16/01/2017.
  */

// FIXME location should be defined in Projectile, but that throws a "Reassignment to val" -error
// FIXME Should direction be Projectiles parameter?
class Fireball(x:Int, y:Int, world:World, var direction: Direction) extends Projectile(x, y, world) {
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png"))
  var speed = 2.0f
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)

}
