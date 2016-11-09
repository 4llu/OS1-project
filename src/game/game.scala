package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 04/11/2016.
  */
object game {

    private val MS_PER_UPDATE = 13
    
    private var renderList = new ArrayBuffer[Renderable]()
    private val updateList = new ArrayBuffer[Updatable]()

    def start(): Unit = {
        this.init()

        var previous: Long = System.currentTimeMillis
        var lag = 0.0

        while (true) {
            val current = System.currentTimeMillis
            var elapsed = current - previous
            previous = current
            lag += elapsed

            this.processInput()

            while (lag >= MS_PER_UPDATE) {
                this.update()
                lag -= MS_PER_UPDATE
            }

            this.draw(lag / MS_PER_UPDATE)
        }
    }
    def update(): Unit = {

    }

    def draw(delta: Double): Unit = {

    }

    def processInput(): Unit = {

    }

    def init(): Unit = {

    }
}
