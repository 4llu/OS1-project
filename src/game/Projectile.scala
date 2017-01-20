package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 16/01/2017.
  */
abstract class Projectile(x:Int, y:Int, world:World, val hitsMonsters: Boolean, val hitsPlayer: Boolean) extends C_Updatable{
  var direction: Direction
  var speed: Float
  val damage: Int
//  var blockedInfo: (Boolean, Option[Monster], Boolean) = _
  
  val collidesWithPlayer = false
  val collidesWithMonsters = false

  def update(timeElapsed: Long): Unit = {
    val blocked = this.location.moveUntilBlocked(this.direction, this.speed*timeElapsed, this)
    for (cell <- this.world.getCellsUnderLocation(this.location)) {
      if (!cell.projectiles.contains(this)) cell.projectiles += this
      if (!this.remove) { 
        for (creature <- cell.creatures) {
          if ((creature == game.player && this.hitsPlayer) || 
              (creature != game.player && this.hitsMonsters)) {
            creature.takeDamage(this.damage)
            this.remove = true
          }
        }
      }
    }
    if (blocked) this.remove = true
//    this.blockedInfo = this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed)
  }
}
