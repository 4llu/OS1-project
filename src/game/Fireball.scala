package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.util.control.Breaks

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
//    if (this.blocked) {
//      val loop = new Breaks
//      loop.breakable {
//        for (monster <- game.monsterList) {
//          if (this.location.overlapsWith(monster.location)) {
//            monster.takeDamage(this.damage)
//            loop.break
//          }
//        }  
//      }
//          
//      this.remove = true
//    }
  }

}
