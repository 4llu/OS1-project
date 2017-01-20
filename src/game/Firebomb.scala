package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * Created by Allu on 18/01/2017.
  */
class Firebomb(x:Int, y:Int, world:World, var direction: Direction, hitsMonsters: Boolean, hitsPlayer: Boolean) 
  extends Projectile(x, y, world, hitsMonsters, hitsPlayer) {
  var speed = 1.0f
  val damage = 30
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png"))
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)

  val timeFired = System.currentTimeMillis()
  val fuse = 1.5

  override def update(timeElapsed: Long): Unit = {
    super.update(timeElapsed)
    if (System.currentTimeMillis() - this.timeFired >= this.fuse) {
      this.explode()
    }
  }

  def explode(): Unit = {
    // TODO Explosion
  }
}
