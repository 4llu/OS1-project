package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * Created by Allu on 18/01/2017.
  */
class Firebomb(x:Int, y:Int, world:World, var direction: Direction, hitsMonsters: Boolean, hitsPlayer: Boolean) 
  extends Projectile(x, y, world, hitsMonsters, hitsPlayer) {
  
  // Stats
  var speed = 1.0f
  val damage = 30
  val explosionRange = 150
  val fuse = 1.5

  // Other
  var sprite: BufferedImage = ImageIO.read(new File("media/fireball.png")) // FIXME Wrong sprite
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)

  // Flags
  var exploding = false
  var  damageDealt = false

  val timeFired = System.currentTimeMillis()

//  override def update(timeElapsed: Long): Unit = {
//    if (!this.exploding) { // Flying
//      super.update(timeElapsed)
//      // If the bomb hit something or the fuse burned up
//      if (this.blockedInfo._1 || System.currentTimeMillis() - this.timeFired >= this.fuse) this.exploding = true
//    }
//    else if (this.exploding && !this.damageDealt) { // Explode (Deal explosion damage and play explosion sfx)
//      // Play sfx
//      Sound.playSoundEffect("fireball") // FIXME wrong sfx
//      // Deal damage
//      for (monster <- game.monsterList) {
//        if (Math.hypot(this.centerX - monster.centerX, this.centerY - monster.centerY) < this.explosionRange) {
//          monster.takeDamage(this.damage)
//        }
//      }
//    }
//    // Play explosion animation
//    if (this.exploding) {
//      // TODO Explosion animation
//    }
//
//
//  }
}
