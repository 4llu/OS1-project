package game

import javax.imageio.ImageIO
import java.io.File

class Healthbar(world: World) extends C_Updatable() {
  var hpGreen = ImageIO.read(new File("media/hp_green.png"))
  var hpRed = ImageIO.read(new File("media/hp_red.png"))
  var sprite = hpGreen
  
  var location = new Location(game.cameraX + 100, game.cameraY + 100, this.sprite.getWidth, this.sprite.getHeight, world) 

  val collidesWithMonsters: Boolean = false
  val collidesWithPlayer: Boolean = false

  def update(timeElapsed: Long): Unit = {
    // Update location to be fixed onto screen bottom
    location = new Location(game.cameraX + 20, game.cameraY + Canvas.height - 40, this.sprite.getWidth, this.sprite.getHeight, world)
    // Update sprite
    val hpRatio = game.player.hp.max(1) / game.player.maxHp.toDouble
    val hpGreenToRatio = hpGreen.getSubimage(0, 0, (this.hpGreen.getWidth * hpRatio).toInt, this.sprite.getHeight)
    this.sprite = Canvas.combineImages(hpRed, hpGreenToRatio, true)
  }
}