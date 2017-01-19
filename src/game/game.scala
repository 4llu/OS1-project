package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer
import java.awt.event._
import scala.swing.event._
import scala.util.Random

object game extends Screen{

  var world:World = _
  var player:Player = _

  // Point system
  var points = 0
  var pointsPrevious = 0
  var combo = 1
  val comboResetTime = (4.0 * 1000).toInt
  var enemyLastKilled = 0L

  // Lists
  var renderList = new ArrayBuffer[C_Drawable]()
  var updateList = new ArrayBuffer[C_Updatable]()
  var monsterList = new ArrayBuffer[Monster]()
  var portalList = new ArrayBuffer[Portal]()
  private var inputList = new ArrayBuffer[(Key.Value, Boolean)]()

  var buttons = ArrayBuffer[Button]()

  val keysPressed = scala.collection.mutable.Map[Key.Value, Boolean](
      (Key.W, false), (Key.A, false), (Key.S, false), (Key.D, false), (Key.Space, false))

  // Camera location
  var cameraX = 0
  var cameraY = 0

  var waveNumber = 0
  var difficulty: Difficulty = _

  val random = new Random()

  /* Initialize the world with the right world and difficulty */
  def init(worldNum: Int, dif: Difficulty): Unit = {
    world = new World(worldNum) // Load the map
    player = new Player(world.width/2, world.height/2, world)
    this.difficulty = dif

    // Set camera position
    cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
    cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2

    // Add the map and player to renderlist
    renderList ++= world.backgroundTiles.flatten
    renderList ++= world.tiles.flatten.filter(_.tileType != "extension")
    renderList += player
  }
    
  def run(): Screen = {

    var previous: Long = System.currentTimeMillis
    var lag = 0.0

    // Start game music
    Sound.playGameMusic()

    // Main game loop
    while (!this.gameEnded) {
      val current = System.currentTimeMillis
      var elapsed = current - previous
      previous = current
      lag += elapsed

      player.update(elapsed)
      this.processInput(elapsed)

      // Wave management
      if (this.monsterList.isEmpty && this.portalList.isEmpty) {
        this.waveNumber += 1
        startWave()
      }

//      while (lag >= this.MS_PER_UPDATE && !this.gameEnded) {
//          this.update(elapsed)
//          lag -= this.MS_PER_UPDATE
//      }
//      this.draw(lag / MS_PER_UPDATE)

      // Update and draw alla game objects
      this.update(elapsed)
      this.draw(this.MS_PER_UPDATE)

    }

    // Game over, return to menu
    menu
  }
  /* Update all game Objects */
  def update(timeElapsed: Long): Unit = {
    // Update lists
    this.updateList.foreach(_.update(timeElapsed))
    this.updateList = this.updateList.filter(!_.remove)
    this.renderList = this.renderList.filter(!_.remove)
    this.monsterList = this.monsterList.filter(!_.remove)
    this.portalList = this.portalList.filter(!_.remove)
    // Update combo counter
    val curTime = System.currentTimeMillis()
    if (this.points != this.pointsPrevious) {
      this.combo += 1
      this.enemyLastKilled = curTime
    }
    // Reset combo
    else if (this.combo != 1 && curTime - this.enemyLastKilled > this.comboResetTime) {
      this.combo = 1
    }
  }

  def processInput(timeElapsed: Long): Unit = {
    for ((key, pressed) <- inputList) {
      keysPressed += key -> pressed
    }

    // Camera follows the player
    cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
    cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2
    inputList.clear()
  }

  /* Game end condition */
  def gameEnded: Boolean = this.player.isDead

  def takeInput(key: Key.Value, pressed: Boolean){
    inputList += ((key, pressed))
  }

  /* Start a new wave */
  def startWave() = {
    // Determine number of portals based on wave number
    val portalCount = if (this.waveNumber < 5) 2 else 3

    // Create portals
    var portals = 0
    while(portals < portalCount){
      // Portal location
      val x = random.nextInt(this.world.map(0).length-1)
      val y = random.nextInt(this.world.map.size-1)
      // Check if valid location
      if (this.world.tiles(y)(x).walkable && this.world.tiles(y)(x+1).walkable &&
          this.world.tiles(y+1)(x).walkable && this.world.tiles(y+1)(x+1).walkable) {
        // Add portal
        this.portalList.append(new Portal(this.world.tiles(y)(x), this.waveNumber, this.difficulty))
        portals += 1
      }
    }
    // Add portals to lists
    this.updateList ++= this.portalList
    this.renderList ++= this.portalList
  }

  /* Add a monster to all relevant lists*/
  def addMonster(monster:Monster) = {
      game.renderList += monster
      game.updateList += monster
      game.monsterList += monster
  }
}
