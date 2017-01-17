package game

import scala.collection.mutable.{ArrayBuffer, Buffer}

/**
  * Created by Allu on 10/11/2016.
  */

import java.io.File
import javax.imageio.ImageIO
import scala.swing.event.Key

class Player(x: Int, y: Int, world: World) extends Creature(x: Int, y: Int, world: World, 1.0f, South, 100) {
  val maxMana = 100
  var mana = this.maxMana
  val weapons = Buffer(new FireballSpell())
  var curWeapon = 0
  var weapon: Weapon = null
  this.weapon = this.weapons(this.curWeapon)

  var sprite = ImageIO.read(new File("media/player.png"))
  
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
      
  def update(timeElapsed: Long): Unit = {
      if (game.keysPressed(Key.W) && game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(NorthWest, this.speed, timeElapsed)
      } else if (game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(NorthEast, this.speed, timeElapsed)
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(SouthEast, this.speed, timeElapsed)
      } else if (!game.keysPressed(Key.W) && game.keysPressed(Key.A) && game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(SouthWest, this.speed, timeElapsed)
              
      } else if (game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(North, this.speed, timeElapsed)
      } else if (!game.keysPressed(Key.W) && game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(West, this.speed, timeElapsed)
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(South, this.speed, timeElapsed)
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(East, this.speed, timeElapsed)
      }
      
      if (game.keysPressed(Key.Space)) {
        if (this.weapon.canFire) {
          game.updateList ++= this.attack()
          this.weapon.lastFired = System.currentTimeMillis
        }
      }
  }

  def attack(): ArrayBuffer[Projectile] = {
    this.weapon.fire(this.location.x, this.location.y, this.world, this.direction)
  }
}
