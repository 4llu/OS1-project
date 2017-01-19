package game

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, speed: Double, maxHp: Int, val points: Int, var range: Int = 1)
        extends Creature(x: Int, y: Int, world: World, speed, maxHp) {

  var weapon: Weapon
  var direction: Direction
  
  var moving = false
  var died = 0L
  val deathAnimationLength: Int
  
  def update(timeElapsed: Long): Unit = {
    // Update normally
    if (!this.isDead) {
      // In attack range
      if (math.hypot(game.player.centerX - this.centerX, game.player.centerY - this.centerY) <= range+this.spriteOffset) {
        this.attack()
      }
      // Move towards player
      else {
        this.move(timeElapsed)
      }
    }
    // Dead, but playing the death animation
    else {
      // Just died
      if (this.died == 0) {
        this.died = System.currentTimeMillis
        game.points += this.points * game.combo
      }
      // Death animation complete
      else if (System.currentTimeMillis - this.died > this.deathAnimationLength*1000) {
        this.remove = true  
      }
      // Playing death animation
      else {
        // TODO Run death animation
      }
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

  def attack() = {
    this.weapon.fire(this.location.x, this.location.y, this.world, this.direction)
  }
  
  def spriteOffset() = {
    if (this.direction == North || this.direction == South) {
      this.sprite.getHeight/2+game.player.sprite.getHeight/2
    } else if (this.direction == West || this.direction == East) {
      this.sprite.getWidth/2+game.player.sprite.getWidth/2
    } else {
      Math.hypot(this.sprite.getWidth/2.0, this.sprite.getHeight/2)+
      Math.hypot(game.player.sprite.getWidth/2.0, game.player.sprite.getHeight/2)
    }
  }
}