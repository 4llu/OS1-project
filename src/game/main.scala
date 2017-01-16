package game

import scala.swing._

object main extends SimpleSwingApplication {

  this.listenTo(Canvas)
    
  val frame = new MainFrame
  frame.title = "Hello World"
  frame.resizable = false
  frame.contents = Canvas
  
  def top = frame
  
  new Thread() {
    override def run {
        game.run(1, Hard)
    }
  }.start()
}