package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.collection.mutable.ArrayBuffer

class Portal(x: Int, y: Int, world: World, waveNumber:Int, difficulty:Difficulty) extends C_Drawable with C_Updatable {
  val onPlayerSide = false
  
  val collidesWithPlayer = false
  val collidesWithMonsters = false

  // Sprites
  private var portal1Sprite = ImageIO.read(new File("media/portal1.png"))
  private var portal2Sprite = ImageIO.read(new File("media/portal2.png")) 
  private var spriteNumber = 0

  // Sprite animation config
  private val baseSpriteChangeCooldown = 0.5
  private var spriteChangeCooldown = baseSpriteChangeCooldown
  
  var sprite:BufferedImage = portal1Sprite
  
  var location = new Location(x, y, sprite.getWidth(), sprite.getHeight(), world)

  // Spawning config
  private val baseSpawnCooldown = 1.0
  private var spawnCooldown = 2.0

  private var waveSize = 0
  private var monsterRatio = ArrayBuffer[Double]()

  private var monstersToSpawn = ArrayBuffer[Monster]()

  // Determine wave size and monster ratio
  if (waveNumber < 5) {         // Waves 0-5
    this.waveSize = (waveNumber*1.5+1).toInt
    monsterRatio = ArrayBuffer[Double](1, 0, 0)
  } else if (waveNumber < 10) { // Waves 6-10
    this.waveSize = (waveNumber * 0.75 + 1).toInt
    monsterRatio = ArrayBuffer[Double](0.8, 0.2, 0)
  } else {                      // The rest of the waves
    this.waveSize = (waveNumber * 0.5 + 1).toInt
    monsterRatio = ArrayBuffer[Double](0.5, 0.3, 0.2)
  }

  // Create monsters
  for (tier <- 0 to 2) {
    for (i <- 0 until (this.waveSize * this.monsterRatio(tier)).toInt) {
      val monster = tier match {
        case 0 => new Goblin(this.location.x, this.location.y, this.location.world)
        case 1 => new Orc(this.location.x, this.location.y, this.location.world)
        case 2 => new Penguin(this.location.x, this.location.y, this.location.world)
      }
      this.monstersToSpawn += monster
    }
  }
  
  def update(timeElapsed:Long) = {
    // Animation
    if (spriteChangeCooldown > 0) {
      spriteChangeCooldown -= timeElapsed/1000.0
    } else {
      spriteChangeCooldown = baseSpriteChangeCooldown
      this.sprite = if (spriteNumber == 0) portal1Sprite else portal2Sprite
      spriteNumber = (spriteNumber+1)%2
    }

    // Monster spawning
    if (spawnCooldown > 0) { // On cooldown
      spawnCooldown -= timeElapsed/1000.0
    } else if (monstersToSpawn.nonEmpty){ // Spawn monster
      var spawnBlocked = false
      for (cell <- this.location.world.getCellsUnderLocation(this.location)) {
        if (cell.isBlockedFor(monstersToSpawn(0))) spawnBlocked = true
      }
      if (!spawnBlocked) {
        spawnCooldown = baseSpawnCooldown + game.random.nextDouble()*0.2-0.1
        game.addMonster(monstersToSpawn(0))
        monstersToSpawn = monstersToSpawn.tail
      }
    } else { // All monsters spawned
      this.remove = true
    }
  }
}