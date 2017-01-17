package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 18/01/2017.
  */
class FirebombSpell(ammo: Int) extends Weapon(ammo, 1.0f) {
  override def fire(x: Int, y: Int, world: World, direction: Direction): ArrayBuffer[Projectile] = {
    ArrayBuffer[Projectile](new Firebomb(x, y, world, direction))
  }
}
