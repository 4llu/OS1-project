package game

import scala.collection.mutable.ArrayBuffer
import scala.swing.event._
import java.io.File
import javax.imageio.ImageIO

object menu extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  })
  
  private var playButton = new Button(100, 100, ImageIO.read(new File("media/UI/play_button.png")), 
      ImageIO.read(new File("media/UI/play_button_hover.png")))
  
  var buttons = ArrayBuffer[Button](playButton)
  var buttonsClicked = ArrayBuffer[Button]()
  
  this.renderList ++= this.buttons
  
  def run() = {
    while(buttonsClicked.isEmpty) {
      buttonsClicked = this.buttons.filter (_.clicked)
      this.draw(this.MS_PER_UPDATE)
    }
    if (buttonsClicked(0) == playButton){
      game.init(1, Hard)
      game
    } else {
      game
    }
  }
}