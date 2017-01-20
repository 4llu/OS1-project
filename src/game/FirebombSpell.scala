package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 18/01/2017.
   */
class FirebombSpell(ammo: Int) extends Spell(ammo, 1.0f) {

  val soundEffect = "fireball_sfx"

  def projectiles(x: Int, y: Int, world: World, direction: Direction): ArrayBuffer[Projectile] = {
    ArrayBuffer[Projectile](new Firebomb(x, y, world, direction, true, false))
  }
}