package game

import scala.collection.mutable.{ArrayBuffer, Buffer}
import java.io.File
import javax.imageio.ImageIO
import scala.swing.event.Key
import java.awt.image.BufferedImage

class Player(x: Int, y: Int, world: World) extends Creature(x: Int, y: Int, world: World, 0.2, 100) {
  val maxMana = 100
  var mana = this.maxMana
  val weapons = Buffer(new FireballSpell())
  var curWeapon = 0
  var weapon: Spell = this.weapons(this.curWeapon)

  this.loadSprites()
  
  var direction:Direction = South
  var sprite = spritesByDirection.get(this.direction).get(1)
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  
  val baseSpriteChangeCooldown = 0.1
      
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
        this.sprite = this.spritesByDirection.get(this.direction).get(1)
        this.spriteChangeCooldown = 0
      }
      
      if (playerMoving) {
        this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed)
        this.walkAnimation(timeElapsed)
      }
      
      if (game.keysPressed(Key.Space)) {
        this.weapon.fire(this.location.x, this.location.y, this.world, this.direction)
      }
  }
  
  def loadSprites() = {
    val playerSprites = ImageIO.read(new File("media/player_sprites.png"))
    val directions = Vector[Direction](
         South, West, East, North, SouthEast, SouthWest, NorthWest, NorthEast)
    for (dir <- 0 to 7) {
      val sprites = ArrayBuffer[BufferedImage]()
      var x = 0
      for (i <- 0 to 2) {
        x = i*32
        if (dir >= 4) {
          x += 192
        }
        sprites += playerSprites.getSubimage(x, (dir%4)*32, 32, 32)
      }
      sprites += playerSprites.getSubimage(x-32, (dir%4)*32, 32, 32)
      spritesByDirection += directions(dir) -> sprites.toVector
    }
  }
}
