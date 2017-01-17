package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
abstract class Weapon(var ammo: Int, attackSpeed: Float) {
  var lastFired: Long = 0;

  def fire(x:Int, y:Int, world:World, direction: Direction): ArrayBuffer[Projectile]

  def canFire: Boolean = System.currentTimeMillis - this.lastFired >= (attackSpeed*1000).toInt
}
