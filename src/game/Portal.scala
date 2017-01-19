package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.collection.mutable.ArrayBuffer

class Portal(tile:Tile, waveNumber:Int, difficulty:Difficulty) extends C_Drawable with C_Updatable {
  private var portal1Sprite = ImageIO.read(new File("media/portal1.png"))
  private var portal2Sprite = ImageIO.read(new File("media/portal2.png")) 
  private var spriteNumber = 0
  
  private val baseSpriteChangeCooldown = 0.5
  private var spriteChangeCooldown = baseSpriteChangeCooldown
  
  var sprite:BufferedImage = portal1Sprite
  
  var location = tile.location
  
  private val baseSpawnCooldown = 3.0
  private var spawnCooldown = 2.0
  
  private var monstersToSpawn = ArrayBuffer[Monster]()
  
  if (waveNumber < 5) {
    for (i <- 0 until (waveNumber*1.5+1).toInt) {
      monstersToSpawn += new Penguin(this.location.x, this.location.y, this.location.world)
    }
  } else if (waveNumber < 10) {
    for (i <- 0 until (waveNumber * 0.75 + 1).toInt) {
      monstersToSpawn += new Penguin(this.location.x, this.location.y, this.location.world)
    }
  } else {
      for (i <- 0 until (waveNumber * 0.5 + 1).toInt) {
        monstersToSpawn += new Penguin(this.location.x, this.location.y, this.location.world)
      }
  }
  
  def update(timeElapsed:Long) = {
    if (spriteChangeCooldown > 0) {
      spriteChangeCooldown -= timeElapsed/1000.0
    } else {
      spriteChangeCooldown = baseSpriteChangeCooldown
      this.sprite = if (spriteNumber == 0) portal1Sprite else portal2Sprite
      spriteNumber = (spriteNumber+1)%2
    }
    if (spawnCooldown > 0) {
      spawnCooldown -= timeElapsed/1000.0
    } else if (!monstersToSpawn.isEmpty){
      spawnCooldown = baseSpawnCooldown + game.random.nextDouble()*0.2-0.1
      game.addMonster(monstersToSpawn(0))
      monstersToSpawn = monstersToSpawn.tail
    }
  }
}