package game

import scala.swing._
import scala.collection.mutable.ArrayBuffer

object main extends SimpleSwingApplication {

  this.listenTo(Canvas)
    
  val frame = new MainFrame
  frame.title = "Hello World"
  frame.resizable = false
  frame.contents = Canvas
  
  def top = frame
  
  Canvas.currentScreen = menu
  
  new Thread() {
    override def run {
      while(true) {
        Canvas.currentScreen  = Canvas.currentScreen.run()
      }
    }
  }.start()
}