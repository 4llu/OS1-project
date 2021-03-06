package game

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Aleksanteri on 15/01/2017.
 */
class FireballSpell extends Spell(0.25) {

  var ammo = -1
  val soundEffect = "fireball" // FIXME Wrong sfx

  def projectiles(x: Int, y: Int, world: World, direction: Direction) = {
    ArrayBuffer[Projectile](new Fireball(x, y, world, direction, true, false))
  }
  
  override def toString: String = "FireballSpell"
}
