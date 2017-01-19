package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer
import java.awt.event._
import scala.swing.event._
import scala.util.Random

object game extends Screen{

    var world:World = _
    var player:Player = _
    
    var points = 0
    var pointsPrevious = 0
    var combo = 1
    val comboResetTime = (4.0 * 1000).toInt
    var enemyLastKilled = 0L

    var renderList = new ArrayBuffer[C_Drawable]()
    var updateList = new ArrayBuffer[C_Updatable]()
    var monsterList = new ArrayBuffer[Monster]()
    private var inputList = new ArrayBuffer[(Key.Value, Boolean)]()
    
    var buttons = ArrayBuffer[Button]()
    
    val keysPressed = scala.collection.mutable.Map[Key.Value, Boolean](
        (Key.W, false), (Key.A, false), (Key.S, false), (Key.D, false), (Key.Space, false))
    
    var cameraX = 0
    var cameraY = 0
    
    var waveNumber = 0
    var difficulty: Difficulty = _
    
    var waveOngoing = false
    
    val random = new Random()

    def init(worldNum: Int, dif: Difficulty): Unit = {
      world = new World(worldNum)
      player = new Player(world.width/2, world.height/2, world)
      
      cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
      cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2
      
      renderList ++= world.backgroundTiles.flatten
      renderList ++= world.tiles.flatten.filter(_.tileType != "extension")
      renderList += player
      
      this.difficulty = dif
    }
    
    def run(): Screen = {
        
        var previous: Long = System.currentTimeMillis
        var lag = 0.0

        Sound.playGameMusic()

        while (!this.gameEnded) {
            val current = System.currentTimeMillis
            var elapsed = current - previous
            previous = current
            lag += elapsed

            player.update(elapsed)
            this.processInput(elapsed)
            
            if (this.monsterList.isEmpty && !waveOngoing) {
              this.waveOngoing = true
              this.waveNumber += 1
              startWave()
            }

            while (lag >= this.MS_PER_UPDATE && !this.gameEnded) {
                this.update(elapsed)
                lag -= this.MS_PER_UPDATE
            }

//            this.draw(lag / MS_PER_UPDATE)
            this.draw(this.MS_PER_UPDATE)
        }

        Sound.stopGameMusic()
        menu
    }
    def update(timeElapsed: Long): Unit = {
      // Update lists
      this.updateList.foreach(_.update(timeElapsed))
      this.updateList = this.updateList.filter(!_.remove)
      this.renderList = this.renderList.filter(!_.remove)
      this.monsterList = this.monsterList.filter(!_.remove)
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
      var input = ""
      for ((key, pressed) <- inputList) {
        keysPressed += key -> pressed
      }
      
      cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
      cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2
      inputList.clear()
    }
    
    def gameEnded: Boolean = this.player.isDead
    
    def takeInput(key: Key.Value, pressed: Boolean){
      inputList += ((key, pressed))
    }
    
    def startWave() = {
      val portalCount = if (this.waveNumber < 5) 2 else 3
      var portals = 0
      while(portals < portalCount){
        val x = random.nextInt(this.world.map(0).length-1)
        val y = random.nextInt(this.world.map.size-1)
        if (this.world.tiles(y)(x).walkable && this.world.tiles(y)(x+1).walkable &&
            this.world.tiles(y+1)(x).walkable && this.world.tiles(y+1)(x+1).walkable) {
          val portal = new Portal(this.world.tiles(y)(x), this.waveNumber, this.difficulty)
          this.renderList += portal
          this.updateList += portal
          portals += 1
        }
      }
    }
    def addMonster(monster:Monster) = {
        game.renderList += monster
        game.updateList += monster
        game.monsterList += monster
    }
}
