package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, speed: Float, dir: Direction, maxHp: Int, val points: Int, val range: Int = 1)
        extends Creature(x: Int, y: Int, world: World, speed, dir, maxHp) {

  var weapon: Weapon

  def update(timeElapsed: Long): Unit = {
    // TODO If close enough to attack / not close enough to attack
    if (math.hypot(game.player.centerX - this.centerX, game.player.centerY - this.centerY) <= range) {
      this.attack()
    }
    else {
      this.move(timeElapsed)
    }
  }

  def move(timeElapsed: Long): Unit = {
    val dx = game.player.centerX - this.centerX
    val dy = game.player.centerY - this.centerY

    if (dx > dy && dx >= 0) this.direction = East
    else if (dx > dy && dx < 0) this.direction = West
    else if (dy > dx && dy >= 0) this.direction = South
    else if (dy > dx && dy < 0) this.direction = North
    else if (dx >= 0 && dy >= 0) this.direction = SouthEast
    else if (dx < 0 && dy >= 0) this.direction = SouthWest
    else if (dx >= 0 && dy < 0) this.direction = NorthEast
    else if (dx < 0 && dy < 0) this.direction = NorthWest

    this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed)
  }

  def attack(): ArrayBuffer[Projectile] = {
    this.weapon.fire(this.location.x, this.location.y, this.world, this.direction)
  }
}
