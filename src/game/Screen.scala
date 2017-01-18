package game

import scala.collection.mutable.ArrayBuffer
import scala.swing.event.MouseClicked

abstract class Screen() {
  
  val MS_PER_UPDATE = 13
    
  var renderList: ArrayBuffer[C_Drawable]
  def run(): Screen
  
  def recieveClick(event:MouseClicked): Unit
  
  def draw(delay: Long): Unit = {
    Canvas.repaint()
    Thread.sleep(delay)
  }
}