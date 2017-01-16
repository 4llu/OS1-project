package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(location: Location, speed: Float, direction: Direction, maxHp: Int, val points: Int) extends Creature(location, speed, direction, maxHp) {
  def move: Unit
  def attack: ArrayBuffer[Projectile] // DEV Just in case we make ranged enemies
}
