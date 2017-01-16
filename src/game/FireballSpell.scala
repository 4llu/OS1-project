package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
class FireballSpell extends Weapon(-1) {
  def fire(location: Location, direction: Direction): ArrayBuffer[Projectile] = {
    ArrayBuffer[Projectile](new Fireball(location, direction))
  }
}
