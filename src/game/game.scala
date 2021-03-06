package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer
import java.awt.event._
import scala.swing.event._
import scala.util.Random
import scala.collection.mutable.Buffer
import java.io.File
import javax.imageio.ImageIO

object game extends Screen{

    // Basics
    var world:World = _
    var player:Player = _
    var hpBar: Healthbar = _
    
    // Point system
    var points:Int = 0
    var pointsPrevious:Int = 0
    var combo:Int = 1
    val comboResetTime = (4.0 * 1000).toInt
    var enemyLastKilled:Long = 0L
    
    //Lists
    var renderList: ArrayBuffer[C_Drawable] =  new ArrayBuffer[C_Drawable]()
    var updateList: ArrayBuffer[C_Updatable] = new ArrayBuffer[C_Updatable]()
    var monsterList: ArrayBuffer[Monster] = new ArrayBuffer[Monster]()
    var portalList: ArrayBuffer[Portal] = new ArrayBuffer[Portal]()
    var dropList = ArrayBuffer[Drop]()
    private var inputList: ArrayBuffer[(Key.Value, Boolean)] = new ArrayBuffer[(Key.Value, Boolean)]()
    
    // Input
    var keysPressed = scala.collection.mutable.Map[Key.Value, Boolean]()
    
    // Camera location
    var cameraX = 0
    var cameraY = 0
    
    // Waves
    var waveNumber:Int = _
    var difficulty: Difficulty = _
    
    val random = new Random()

    // XXX Difficulty works, but there is no way too chose it
    /* Initialize the world with the right world and difficulty */
    def init(worldNum: Int, dif: Difficulty): Unit = {
      this.world = new World(worldNum) // Load the map
      this.player = new Player(world.width/2, world.height/2, world)
      this.hpBar = new Healthbar(this.world)
      
      // Set camera position
      this.cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
      this.cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2
      
      // Empty lists
      this.renderList.clear()
      this.updateList.clear() 
      this.monsterList.clear()
      this.portalList.clear()
      this.dropList.clear()
      
      // Reset Input
      this.inputList.clear()
      this.keysPressed = scala.collection.mutable.Map[Key.Value, Boolean](
        (Key.W, false), (Key.A, false), (Key.S, false), (Key.D, false), (Key.Space, false),
        (Key.K, false), (Key.M, false))
      
      // Add the map and player to renderlist
      this.renderList ++= world.backgroundTiles.flatten
      this.renderList ++= world.tiles.flatten.filter(_.tileType != "extension")
      this.renderList += player
      
      // Add hp bar to relevant lists
      this.addGameObject(this.hpBar)
      
      // Reset point system
      this.points = 0
      this.pointsPrevious = 0
      this.combo = 1
      this.enemyLastKilled = 0L
      this.waveNumber = 0
      this.difficulty = dif
    }
    
    /* Starts the game screen */
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
              // Start new wave
              this.waveNumber += 1
              this.startWave()
              // Drops spawn at the start of the wave
              this.dropItems()
            }

            while (lag >= this.MS_PER_UPDATE && !this.gameEnded) {
                this.update(elapsed)
                lag -= this.MS_PER_UPDATE
            }
      //      this.draw(lag / MS_PER_UPDATE)
      
            // Update and draw all game objects
//            this.update(elapsed)
            this.draw(this.MS_PER_UPDATE)
        }

        // Recenter camera
        cameraX = 0
        cameraY = 0
        // Game over, move to game over screen
        gameOver
    }
    
    /* Update all game Objects */
    def update(timeElapsed: Long): Unit = {
      // Update lists
      this.updateList.foreach(_.update(timeElapsed))
      updateCells(updateList.toBuffer[C_Drawable])
      this.updateList = this.updateList.filter(!_.remove)
      this.renderList = this.renderList.filter(!_.remove)
      this.monsterList = this.monsterList.filter(!_.remove)
      this.portalList = this.portalList.filter(!_.remove)
      this.dropList = this.dropList.filter(!_.remove)
      
      // XXX Too late!!!!
      // Combos work, but there is no time to make a visible combo counter, so it is disabled for now
      // Update combo counter
      val curTime = System.currentTimeMillis() 
      if (this.points != this.pointsPrevious) {
        this.combo += 0 // XXX 0 -> 1 and combos works
        this.enemyLastKilled = curTime
      }
      // Reset combo
      else if (this.combo != 1 && curTime - this.enemyLastKilled > this.comboResetTime) {
        this.combo = 1
      }

    }

  /* Process player input */
  def processInput(timeElapsed: Long): Unit = {
    for ((key, pressed) <- inputList) {
      keysPressed += key -> pressed
    }

    // Camera follows the player
    cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
    cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2
    inputList.clear()
  }

  /* Register player input */
  def takeInput(key: Key.Value, pressed: Boolean){
    inputList += ((key, pressed))
  }

  /* Game end condition */
  def gameEnded: Boolean = this.player.isDead

  /* Start a new wave */
  def startWave(): Unit = {
    // Determine number of portals based on wave number
    // val portalCount = if (this.waveNumber < 5) 2 else 3
    val portalCount = this.waveNumber / 7 + 2

    // Create portals
    var portals = 0
    while(portals < portalCount){
      // Portal location
      val x = random.nextInt(this.world.width)
      val y = random.nextInt(this.world.height)
      // Check if valid location
      val portal = new Portal(x, y, this.world, this.waveNumber, this.difficulty)
      var locationBlocked = false
      for (cell <- this.world.getCellsUnderLocation(portal.location)) {
        if (!cell.walkable) locationBlocked = true
      }
      if (!locationBlocked) {
        // Add portal
        this.addPortal(portal)
        portals += 1
      }
    }
  }
  
  /* Drop items */
  def dropItems(): Unit = {
    // Determine number of drops based on wave number
    val dropNum = if (this.waveNumber < 10) 1 else if (this.waveNumber < 20) 2 else 3
    
    // Create drops
    var drops = 0
    while(drops < dropNum){
      // Drop location
      val x = random.nextInt(this.world.width)
      val y = random.nextInt(this.world.height)
        // Select random drop
      val randomDrop = if (this.random.nextDouble() < 0.3) new SpellDrop(x, y, this.world, "FirebombSpell")
                        else if (this.random.nextDouble() < 0.6) new SpellDrop(x, y, this.world, "IceShardSpell")
                        else new HealthDrop(x, y, this.world)
      // Check if valid location
      var locationBlocked = false
      for (cell <- this.world.getCellsUnderLocation(randomDrop.location)) {
        if (!cell.walkable) locationBlocked = true
      }
      if (!locationBlocked) {
        // Add to lists
        this.addDrop(randomDrop)
        drops += 1
      }
    }
    
  }
  
  def updateCells(list:Buffer[C_Drawable]): Unit = {
    for (obj <- list.filter(_.remove)){
      for (cell <- this.world.getCellsUnderLocation(obj.location)) {
        cell.creatures = cell.creatures.filter(_ != obj)
        cell.projectiles = cell.projectiles.filter(_ != obj)
      }
    }
  }

  /* Add a monster to all relevant lists*/
  def addMonster(monster:Monster): Unit = {
    this.addGameObject(monster)
    game.monsterList += monster
  }
  
  /* Add a portal to all relevant lists*/
  def addPortal(portal:Portal): Unit = {
    this.addGameObject(portal)
    game.portalList += portal
  }
  
  /* Add a drop to all relevant lists*/
  def addDrop(drop:Drop): Unit = {
    this.addGameObject(drop)
    game.dropList += drop
  }
  
  /* Add a game object to the update and render lists */
  def addGameObject(go: C_Updatable): Unit = {
      game.renderList += go
      game.updateList += go
  }
}
