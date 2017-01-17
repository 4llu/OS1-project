package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, speed: Float, direction: Direction, maxHp: Int, val points: Int, val range: Int = 1)
        extends Creature(x: Int, y: Int, world: World, speed, direction, maxHp) {


  def update(timeElapsed: Long): Unit = {
    // TODO If close enough to attack / not close enough to attack
    if (true) {
      this.attack // FIXME Why can't I add ()?
    }
    else {
      this.move()
    }
  }

  def move: Unit = {
    // TODO
  }

  def attack: ArrayBuffer[Projectile] = {
    // TODO
    ArrayBuffer[Projectile]()
  }
}
