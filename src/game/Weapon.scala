package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Aleksanteri on 15/01/2017.
  */
abstract class Weapon(var ammo: Int) {
  def fire(location: Location, direction: Direction): ArrayBuffer[Projectile]
}
