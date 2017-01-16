package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer
import java.awt.Dimension
import java.awt.event._
import scala.swing.event._

object game {

    private val MS_PER_UPDATE = 13
    
    val world = new World()
    val player = new Player(new Location(100, 200, 5, 5, this.world))

    private var renderList = new ArrayBuffer[Renderable]()
    private var updateList = new ArrayBuffer[Updatable]()
    private var inputList = new ArrayBuffer[String]()
    updateList += this.player
   
    var cameraX = player.location.x-Canvas.width/2
    var cameraY = player.location.y-Canvas.height/2

    def start(worldNum: Int, dif: Difficulty): Unit = {
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
            println(elapsed)

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
      for (input <- inputList) {
        if(input == "w") {
          player.location = player.location.move(0, (-player.speed*timeElapsed).toInt)
        } else if(input == "a") {
          player.location = player.location.move((-player.speed*timeElapsed).toInt, 0)
        } else if(input == "s") {
          player.location = player.location.move(0, (player.speed*timeElapsed).toInt)
        } else if(input == "d") {
          player.location = player.location.move((player.speed*timeElapsed).toInt, 0)
        } else if(input == "click") {
          println("click")
        }
      }
      cameraX = player.location.x-Canvas.width/2
      cameraY = player.location.y-Canvas.height/2
      inputList.clear()
    }

    def init(): Unit = {
      
    }

    def gameEnded: Boolean = this.player.isDead
    
    def takeInput(input: String){
      inputList += input
    }
}
