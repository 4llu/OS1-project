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
          this.location = this.location.moveUntilBlocked(
              -this.speed*timeElapsed/Math.sqrt(2.0), -this.speed*timeElapsed/Math.sqrt(2.0))
      } else if (game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(
              this.speed*timeElapsed/Math.sqrt(2.0), -this.speed*timeElapsed/Math.sqrt(2.0))
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(
              this.speed*timeElapsed/Math.sqrt(2.0), this.speed*timeElapsed/Math.sqrt(2.0))
      } else if (!game.keysPressed(Key.W) && game.keysPressed(Key.A) && game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(
              -this.speed*timeElapsed/Math.sqrt(2.0), this.speed*timeElapsed/Math.sqrt(2.0))
              
      } else if (game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(0, -this.speed*timeElapsed)
      } else if (!game.keysPressed(Key.W) && game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(-this.speed*timeElapsed, 0)
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(0, this.speed*timeElapsed)
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.location = this.location.moveUntilBlocked(this.speed*timeElapsed, 0)
      }
      
      if (game.keysPressed(Key.Space)) {
          game.updateList ++= this.attack()
      }
  }

  def attack(): ArrayBuffer[Projectile] = {
    this.weapon.fire(this.location, this.direction)
  }
}
