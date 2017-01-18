package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
abstract class Weapon(var ammo: Int, attackSpeed: Double) {
  var lastFired: Long = 0;

  def projectiles(x:Int, y:Int, world:World, direction: Direction): ArrayBuffer[Projectile]
  
  def fire(x:Int, y:Int, world: World, direction: Direction) = {
    if (this.canFire) {
      val projectiles = this.projectiles(x, y, world, direction)
      game.updateList ++= projectiles
      game.renderList ++= projectiles
      this.lastFired = System.currentTimeMillis
    }
  }

  def canFire: Boolean = System.currentTimeMillis - this.lastFired >= (attackSpeed*1000).toInt
}
