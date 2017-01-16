package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * Created by Allu on 16/01/2017.
  */

// FIXME location should be defined in Projectile, but that throws a "Reassignment to val" -error
// FIXME Should direction be Projectiles parameter?
class Fireball(var location: Location, val direction: Direction) extends Projectile {
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png"))
  var speed = 2.0f

  def update(timeElapsed: Long): Unit = {
    if (this.direction == North) {
      this.location = this.location.move(0, -(this.speed * timeElapsed).toInt)
    }
    else if (this.direction == East) {
      this.location = this.location.move((this.speed * timeElapsed).toInt, 0)
    }
    else if (this.direction == South) {
      this.location = this.location.move(0, (this.speed * timeElapsed).toInt)
    }
    else if (this.direction == West) {
      this.location = this.location.move(-(this.speed * timeElapsed).toInt, 0)
    }

  }
}
