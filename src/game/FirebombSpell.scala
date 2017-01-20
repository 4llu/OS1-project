package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 18/01/2017.
   */
class FirebombSpell() extends Spell(1.0f) {

  var ammo = 15
  val soundEffect = "fireball_sfx"

  def projectiles(x: Int, y: Int, world: World, direction: Direction): ArrayBuffer[Projectile] = {
    ArrayBuffer[Projectile](new Firebomb(x, y, world, direction, true, false))
  }
  
  override def toString(): String = "FirebombSpell"
}