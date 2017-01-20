package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Melee (x:Int, y:Int, world:World, var direction: Direction, val damage: Int, hitsMonsters: Boolean, hitsPlayer: Boolean) 
  extends Projectile(x, y, world, hitsMonsters, hitsPlayer) {
  var speed = 0.3f
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png")) // FIXME wrong sprite
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  val created = System.currentTimeMillis()
  val range = 40
  
  override def update(timeElapsed: Long): Unit =  {
    super.update(timeElapsed)
    
    // Remove at the edge of the weapons range
    if ((System.currentTimeMillis() - this.created) * this.speed > range) this.remove = true
    // If player hit
    if (this.location.overlapsWith(game.player.location)) {
      game.player.takeDamage(this.damage)
      this.remove = true
    }
  }
}