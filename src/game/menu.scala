package game

import scala.collection.mutable.ArrayBuffer
import scala.swing.event._
import java.io.File
import javax.imageio.ImageIO

object menu extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  }, 
  new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/the_last_mage.png"))
    var location = new Location(244, 186, sprite.getWidth, sprite.getHeight, null)
  })
  
  private var playButton = new Button(205, 278, ImageIO.read(new File("media/UI/play_button.png")), 
      ImageIO.read(new File("media/UI/play_button_hover.png")))
  private var highscoresButton = new Button(205, 338, ImageIO.read(new File("media/UI/highscores_button.png")), 
      ImageIO.read(new File("media/UI/highscores_button_hover.png")))
  private var optionsButton = new Button(205, 397, ImageIO.read(new File("media/UI/options_button.png")), 
      ImageIO.read(new File("media/UI/options_button_hover.png")))
  
  this.buttons = ArrayBuffer[Button](playButton, highscoresButton, optionsButton)
  
  this.renderList ++= this.buttons
  
  def run() = {

    Sound.playMenuMusic()

    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (playButton.clicked){
      playButton.clicked = false
      mapSelection
    } else if (highscoresButton.clicked){
      highscoresButton.clicked = false
      menu
    } else {
      optionsButton.clicked = false
      menu
    }
  }
}

object mapSelection extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  })
  
  private var playButton = new Button(205, 278, ImageIO.read(new File("media/UI/play_button.png")), 
      ImageIO.read(new File("media/UI/play_button_hover.png")))
  
  this.buttons = ArrayBuffer[Button](playButton)
  
  this.renderList ++= this.buttons
  
  def run() = {
    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (playButton.clicked){
      playButton.clicked = false
      game.init(2, Medium)
      game
    } else {
      menu
    }
  }
}