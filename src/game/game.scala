package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 04/11/2016.
  */
object game {

    private val MS_PER_UPDATE = 13

    private val player = new Player()
    private val world = new World()

    private val renderList = new ArrayBuffer[Renderable]()
    private val updateList = new ArrayBuffer[Updatable]()
    updateList += this.player

    def start(world: World, dif: Difficulty): Unit = {
        this.init() // FIXME Is this necessary?

        var previous: Long = System.currentTimeMillis
        var lag = 0.0

        while (!this.gameEnded) {
            val current = System.currentTimeMillis
            var elapsed = current - previous
            previous = current
            lag += elapsed

            this.processInput()

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

    }

    def processInput(): Unit = {

    }

    def init(): Unit = {
    }

    def gameEnded: Boolean = this.player.isDead
}
