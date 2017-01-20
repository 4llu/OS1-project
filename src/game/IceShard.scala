package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 20/01/2017.
  */
class IceShard(x:Int, y:Int, world:World, var direction: Direction) extends Projectile(x, y, world) {
  var speed = 1.2f
  val damage = 15
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png")) // FIXME Wrong sprite
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  val monstersHit = ArrayBuffer[Monster]()

  override def update(timeElapsed: Long): Unit = {
    // Move, but ignore monsters as blockers
    this.blockedInfo = this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed, true)

    // Deal damage
    for (monster <- game.monsterList) {
      // Damage monsters that the projectile is hitting now that haven't been damaged yet
      if (!this.monstersHit.contains(monster) && this.location.overlapsWith(monster.location)) {
        monster.takeDamage(this.damage)
        this.monstersHit += monster
      }
    }

    // Remove if blocked by terrain
    if (this.blockedInfo._1) this.remove
  }
}