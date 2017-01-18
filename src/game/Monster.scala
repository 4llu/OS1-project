package game

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, speed: Double, maxHp: Int, val points: Int, val range: Int = 1)
        extends Creature(x: Int, y: Int, world: World, speed, maxHp) {

  var weapon: Weapon
  var direction: Direction
  
  var sprite: BufferedImage
  
  var moving = false
  
  def update(timeElapsed: Long): Unit = {
    // TODO If close enough to attack / not close enough to attack
    if (math.hypot(game.player.centerX - this.centerX, game.player.centerY - this.centerY) <= range) {
      this.attack()
    }
    else {
      this.move(timeElapsed)
    }
  }
  
  def playerDirection():Direction = {
    val dx = game.player.centerX - this.centerX
    val dy = game.player.centerY - this.centerY
    
    if (dx > 0 && dy > 0) SouthEast
    else if (dx < 0 && dy > 0) SouthWest
    else if (dx > 0 && dy < 0) NorthEast
    else if (dx < 0 && dy < 0) NorthWest
    else if (dx == 0 && dy < 0) North
    else if (dx == 0 && dy > 0) South
    else if (dx > 0 && dy == 0) East
    else West
    
  }

  def move(timeElapsed: Long): Unit = {
    this.direction = this.playerDirection()
    this.moving = true
    this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed)
    this.moving = false
  }

  def attack(): ArrayBuffer[Projectile] = {
    this.weapon.fire(this.location.x, this.location.y, this.world, this.direction)
  }
}