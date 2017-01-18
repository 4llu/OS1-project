package game

import scala.collection.mutable.ArrayBuffer
import scala.swing.event.MouseClicked

object menu extends Screen {
  var renderList = ArrayBuffer[C_Drawable]()
  var clicked = false
  def run() = {
    while(!clicked) {
      this.draw(this.MS_PER_UPDATE)
    }
    
    game.init(1, Hard)
    game
  }
  
  def recieveClick(event:MouseClicked) = {
    clicked = true
  }
}