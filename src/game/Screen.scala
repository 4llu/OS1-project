package game

import scala.collection.mutable.ArrayBuffer
import scala.swing.event._

abstract class Screen() {
  
  val MS_PER_UPDATE = 13
    
  var buttons = ArrayBuffer[Button]()
  
  var renderList: ArrayBuffer[C_Drawable]
  def run(): Screen
  
  def draw(delay: Long): Unit = {
    Canvas.repaint()
    Thread.sleep(delay)
  }
  
  def recieveClick(event:MouseClicked) = {
//    println(event.point.getX+", "+event.point.getY)
    for (button <- this.buttons) {
      if (button.hover) button.clicked = true
    }
  }
  
  def recieveMouseMovement(event:MouseMoved) = {
    val mouseLocation = new Location(event.point.getX.toInt, event.point.getY.toInt, 1, 1, null)
    for (button <- this.buttons) {
      if (button.location.overlapsWith(mouseLocation)) {
        button.hoverTrue()
      } else {
        button.hoverFalse()
      }
    }
  }
}