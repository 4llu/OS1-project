package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, speed: Float, direction: Direction, maxHp: Int, val points: Int) 
        extends Creature(x: Int, y: Int, world: World, speed, direction, maxHp) {
  def move: Unit
  def attack: ArrayBuffer[Projectile] // DEV Just in case we make ranged enemies
}
