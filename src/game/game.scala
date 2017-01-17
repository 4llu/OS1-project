package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer
import java.awt.event._
import scala.swing.event._

object game extends Screen{

    private val MS_PER_UPDATE = 13
    
    var world:World = null
    var player:Player = null

    var renderList = new ArrayBuffer[C_Drawable]()
    var updateList = new ArrayBuffer[C_Updatable]()
    private var inputList = new ArrayBuffer[(Key.Value, Boolean)]()
//    updateList += this.player
    
    val keysPressed = scala.collection.mutable.Map[Key.Value, Boolean](
        (Key.W, false), (Key.A, false), (Key.S, false), (Key.D, false), (Key.Space, false))
    
    var cameraX = 0
    var cameraY = 0

    def init(worldNum: Int, dif: Difficulty) = {
      world = new World(worldNum)
      player = new Player(world.width/2, world.height/2, world)
      
      cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
      cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2
      
      renderList ++= world.backgroundTiles.flatten
      renderList ++= world.tiles.flatten.filter(_.tileType != "extension")
      renderList += player
    }
    
    def run(): Int = {
        
        var previous: Long = System.currentTimeMillis
        var lag = 0.0

        while (!this.gameEnded) {
            val current = System.currentTimeMillis
            var elapsed = current - previous
            previous = current
            lag += elapsed

            player.update(elapsed)
            this.processInput(elapsed)

            while (lag >= MS_PER_UPDATE && !this.gameEnded) {
                this.update(elapsed)
                lag -= MS_PER_UPDATE
            }

            this.draw(lag / MS_PER_UPDATE)
        }
        
        1
        // TODO Ending screen and return to menu
    }
    def update(timeElapsed: Long): Unit = {
        this.updateList.foreach(n => n.update(timeElapsed))
    }

    def draw(delta: Double): Unit = {
      Canvas.repaint()
      Thread.sleep(MS_PER_UPDATE)
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

    def init(): Unit = {
      
    }

    def gameEnded: Boolean = this.player.isDead
    
    def takeInput(key: Key.Value, pressed: Boolean){
      inputList += ((key, pressed))
    }
}
