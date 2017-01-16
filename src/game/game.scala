package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer
import java.awt.event._
import scala.swing.event._

object game {

    private val MS_PER_UPDATE = 13
    
    val world = new World()
    val player = new Player(100, 200, this.world)

    private var renderList = new ArrayBuffer[C_Renderable]()
    private var updateList = new ArrayBuffer[C_Updatable]()
    private var inputList = new ArrayBuffer[(Key.Value, Boolean)]()
    updateList += this.player
    
    private val keysPressed = scala.collection.mutable.Map[Key.Value, Boolean](
        (Key.W, false), (Key.A, false), (Key.S, false), (Key.D, false))
    
    var cameraX = player.location.x+player.sprite.getWidth/2-Canvas.width/2
    var cameraY = player.location.y+player.sprite.getHeight/2-Canvas.height/2

    def run(worldNum: Int, dif: Difficulty): Unit = {
        // FIXME Turn worldNum into a world
        this.init() // FIXME Is this necessary?
        
        var previous: Long = System.currentTimeMillis
        var lag = 0.0

        while (!this.gameEnded) {
            val current = System.currentTimeMillis
            var elapsed = current - previous
            previous = current
            lag += elapsed

            this.processInput(elapsed)

            while (lag >= MS_PER_UPDATE && !this.gameEnded) {
                this.update()
                lag -= MS_PER_UPDATE
            }

            this.draw(lag / MS_PER_UPDATE)
        }

        // TODO Ending screen and return to menu
    }
    def update(): Unit = {
        this.updateList.foreach(n => n.update())
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
      
      if (keysPressed(Key.W) && keysPressed(Key.A) && !keysPressed(Key.S) && !keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(
              -player.speed*timeElapsed/Math.sqrt(2.0), -player.speed*timeElapsed/Math.sqrt(2.0))
      } else if (keysPressed(Key.W) && !keysPressed(Key.A) && !keysPressed(Key.S) && keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(
              player.speed*timeElapsed/Math.sqrt(2.0), -player.speed*timeElapsed/Math.sqrt(2.0))
      } else if (!keysPressed(Key.W) && !keysPressed(Key.A) && keysPressed(Key.S) && keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(
              player.speed*timeElapsed/Math.sqrt(2.0), player.speed*timeElapsed/Math.sqrt(2.0))
      } else if (!keysPressed(Key.W) && keysPressed(Key.A) && keysPressed(Key.S) && !keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(
              -player.speed*timeElapsed/Math.sqrt(2.0), player.speed*timeElapsed/Math.sqrt(2.0))
              
      } else if (keysPressed(Key.W) && !keysPressed(Key.A) && !keysPressed(Key.S) && !keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(0, -player.speed*timeElapsed)
      } else if (!keysPressed(Key.W) && keysPressed(Key.A) && !keysPressed(Key.S) && !keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(-player.speed*timeElapsed, 0)
      } else if (!keysPressed(Key.W) && !keysPressed(Key.A) && keysPressed(Key.S) && !keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(0, player.speed*timeElapsed)
      } else if (!keysPressed(Key.W) && !keysPressed(Key.A) && !keysPressed(Key.S) && keysPressed(Key.D)) {
          player.location = player.location.moveUntilBlocked(player.speed*timeElapsed, 0)
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
