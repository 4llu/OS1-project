package game

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage
import scala.collection.mutable.Buffer

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, speed: Double, maxHp: Int, val points: Int, var range: Int = 1)
        extends Creature(x: Int, y: Int, world: World, speed, (maxHp*game.difficulty.hpMod).toInt) {

  var weapon: Spell
  var direction: Direction
  
  var died = 0L // Time of death (for death animation)
  val deathAnimationLength: Int
  
  val collidesWithPlayer = true
  val collidesWithMonsters = true
  
  val baseSearchForNewPathCooldown = 2.0
  var searchForNewPathCooldown = 0.0
  
  var path = Buffer[Cell]()
  
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
        this.walkAnimation(timeElapsed)
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

  def move(timeElapsed: Long): Unit = {
    if (this.searchForNewPathCooldown > 0) {
      this.searchForNewPathCooldown -= timeElapsed/1000.0
    } else {
      this.searchForNewPathCooldown = this.baseSearchForNewPathCooldown
      this.path = PathFinding.aStar(this, 
          this.world.getCell(game.player.centerX, game.player.centerY), this.world)
    }
    if (!this.path.isEmpty) {
      this.path = this.location.moveAlongPath(path, this.speed*timeElapsed, this)
      for (cell <- this.world.getCellsUnderLocation(this.location)) {
        if (!cell.creatures.contains(this)) cell.creatures += this
      }
//      if (!this.path.isEmpty) this.direction = this.getDirection(path(0))
//      else this.direction = this.getDirection(game.player)
    }
    this.direction = this.getDirection(game.player)
  }

  def attack(): Unit = {
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