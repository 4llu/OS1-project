package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * Created by Allu on 16/01/2017.
  */

class Fireball(x:Int, y:Int, world:World, var direction: Direction) extends Projectile(x, y, world) {
  var speed = 1.0f
  val damage = 10
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png"))
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)

  override def update(timeElapsed: Long): Unit = {
    super.update(timeElapsed)

    // FIXME Optimize
    // FIXME Can this be generalized to projectiles somehow
    if (this.blockedInfo._1) {
      // Damage an enemy if one is hit
      this.blockedInfo._2.foreach(_.takeDamage(this.damage))
      this.remove = true
    }
  }

}
