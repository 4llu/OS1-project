package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
abstract class Spell(attackSpeed: Double) {

  var ammo: Int
  val soundEffect: String
  var lastFired: Long = 0;
  def projectiles(x:Int, y:Int, world:World, direction: Direction): ArrayBuffer[Projectile]
  
  def fire(x:Int, y:Int, world: World, direction: Direction): Unit = {
    if (this.canFire) {
      // Play sfx
      Sound.playSoundEffect(this.soundEffect)
      val projectiles = this.projectiles(x, y, world, direction)
      game.updateList ++= projectiles
      game.renderList ++= projectiles
      this.lastFired = System.currentTimeMillis
      // If not unlimited ammo, use one ammo
      if (this.ammo != -1) this.ammo -= 1
    }
  }

  def canFire: Boolean = System.currentTimeMillis - this.lastFired >= (attackSpeed*1000).toInt
}
