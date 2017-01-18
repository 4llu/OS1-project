package game

import scala.collection.mutable.{ArrayBuffer, Buffer}

/**
  * Created by Allu on 10/11/2016.
  */

import java.io.File
import javax.imageio.ImageIO
import scala.swing.event.Key

class Player(x: Int, y: Int, world: World) extends Creature(x: Int, y: Int, world: World, 0.2, 100) {
  val maxMana = 100
  var mana = this.maxMana
  val weapons = Buffer(new FireballSpell())
  var curWeapon = 0
  var weapon: Weapon = this.weapons(this.curWeapon)

  var sprite = ImageIO.read(new File("media/player.png"))
  
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  
  var direction:Direction = South
      
  def update(timeElapsed: Long): Unit = {
      var playerMoving = true 
      if (game.keysPressed(Key.W) && game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
        this.direction = NorthWest  
      } else if (game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
        this.direction = NorthEast
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
        this.direction = SouthEast
      } else if (!game.keysPressed(Key.W) && game.keysPressed(Key.A) && game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
        this.direction = SouthWest
              
      } else if (game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.direction = North
      } else if (!game.keysPressed(Key.W) && game.keysPressed(Key.A) && !game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.direction = West
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && game.keysPressed(Key.S) && !game.keysPressed(Key.D)) {
          this.direction = South
      } else if (!game.keysPressed(Key.W) && !game.keysPressed(Key.A) && !game.keysPressed(Key.S) && game.keysPressed(Key.D)) {
          this.direction = East
      } else {
        playerMoving = false
      }
      
      if (playerMoving) {
        this.location = this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed)
      }
      
      if (game.keysPressed(Key.Space)) {
        if (this.weapon.canFire) {
          val projectiles = this.attack()
          game.updateList ++= projectiles
          game.renderList ++= projectiles
          this.weapon.lastFired = System.currentTimeMillis
        }
      }
  }

  def attack(): ArrayBuffer[Projectile] = {
    this.weapon.fire(this.location.x, this.location.y, this.world, this.direction)
  }
}
