package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
class FireballSpell extends Weapon(-1, 0.25) {
  def fire(x:Int, y:Int, world: World, direction: Direction): ArrayBuffer[Projectile] = {
    ArrayBuffer[Projectile](new Fireball(x, y, world, direction))
  }
}
