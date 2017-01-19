package game

import scala.collection.mutable.ArrayBuffer

class MeleeSpell(val damage: Int) extends Spell(-1, 1.5) {
  val soundEffect = "fireball_sfx" // FIXME wrong sound effect

  def projectiles(x:Int, y:Int, world: World, direction: Direction) = {
    ArrayBuffer[Projectile](new Melee(x, y, world, direction, damage))
  }
}