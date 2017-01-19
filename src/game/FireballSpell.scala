package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
class FireballSpell extends Spell(-1, 0.25) {

  val soundEffect = "fireball_sfx"

  def projectiles(x:Int, y:Int, world: World, direction: Direction) = {
    ArrayBuffer[Projectile](new Fireball(x, y, world, direction))
  }
}
