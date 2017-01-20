package game

import scala.collection.mutable.ArrayBuffer
import scala.swing.event._
import java.io.File
import javax.imageio.ImageIO
import scala.swing.Label
import scala.swing.GridPanel
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.Frame

//////////
// MENU //
//////////

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
  private var instructionsButton = new Button(205, 397, ImageIO.read(new File("media/UI/instructions_button.png")), 
      ImageIO.read(new File("media/UI/instructions_button_hover.png")))
  private var musicButton = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_on.png")))
  private var sfxButton = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_on.png")))
  private var musicButtonOff = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_off.png")))
  private var sfxButtonOff = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_off.png")))
  
  this.buttons = ArrayBuffer[Button](playButton, highscoresButton, instructionsButton, musicButton, sfxButton)
  
  this.renderList ++= this.buttons
  
  def run() = {

    Sound.playMenuMusic()
      
    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (playButton.clicked){ // Play
      playButton.clicked = false
      mapSelection
    } else if (highscoresButton.clicked){ // Highscores
      highscoresButton.clicked = false
      highscores
    } else if (instructionsButton.clicked){ // Instructions 
      instructionsButton.clicked = false
      instructions
    } else if (musicButton.clicked) { // Music mute
      musicButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= musicButton
      this.buttons += musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = true
      Sound.stopGameMusic()
      Sound.stopMenuMusic()
      
      menu
    } else if (sfxButton.clicked) { // SFX mute
      sfxButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= sfxButton
      this.buttons += sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = true // Mute
      
      menu
    } else if (musicButtonOff.clicked) { // Music on
      musicButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += musicButton
      this.buttons -= musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = false
      if (Sound.inMenu) Sound.playMenuMusic() else Sound.playGameMusic()
      
      menu
    } else if (sfxButtonOff.clicked) { // SFX on
      sfxButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += sfxButton
      this.buttons -= sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = false
      
      menu
    } else {
      menu
    }
  }
}

///////////////////
// MAP SELECTION //
///////////////////

object mapSelection extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  },
  // Map previews
  new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/bush_map_preview.png"))
    var location = new Location(96, 114, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/plains_map_preview.png"))
    var location = new Location(96, 354, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/high_ground_map_preview.png"))
    var location = new Location(454, 114, sprite.getWidth, sprite.getHeight, null)
  },
  // Choose map text
  new C_Drawable() {
    var sprite = ImageIO.read(new File("media/UI/choose_a_map.png"))
    var location = new Location(319, 40, sprite.getWidth, sprite.getHeight, null)
  })
  
  // Map buttons
  private var bushButton = new Button(70, 260, ImageIO.read(new File("media/UI/bush_map_button.png")), 
      ImageIO.read(new File("media/UI/bush_map_button_hover.png")))
  private var plainsButton = new Button(70, 500, ImageIO.read(new File("media/UI/plains_map_button.png")), 
      ImageIO.read(new File("media/UI/plains_map_button_hover.png")))
  private var highGroundButton = new Button(428, 260, ImageIO.read(new File("media/UI/high_ground_map_button.png")), 
      ImageIO.read(new File("media/UI/high_ground_map_button_hover.png")))
  
  // Return button
  private var returnButton = new Button(34, 592, ImageIO.read(new File("media/UI/return_button.png")), 
      ImageIO.read(new File("media/UI/return_button_hover.png")))
  
  // Sound buttons
  private var musicButton = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_on.png")))
  private var sfxButton = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_on.png")))
  private var musicButtonOff = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_off.png")))
  private var sfxButtonOff = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_off.png")))
  
  this.buttons = ArrayBuffer[Button](bushButton, plainsButton, highGroundButton, returnButton, musicButton, sfxButton)
  
  this.renderList ++= this.buttons
  
  def run() = {
    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (returnButton.clicked){ // Return
      returnButton.clicked = false
      
      menu
    } else if(bushButton.clicked) { // Bush
      bushButton.clicked = false
      game.init(1, Medium)
      game
    } else if(plainsButton.clicked) { // Plains
      plainsButton.clicked = false
      game.init(2, Medium)
      game
    } else if(highGroundButton.clicked) { // High grounds
      highGroundButton.clicked = false
      game.init(3, Medium)
      game
    } else if (musicButton.clicked) { // Music mute
      musicButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= musicButton
      this.buttons += musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = true
      Sound.stopGameMusic()
      Sound.stopMenuMusic()
      
      menu
    } else if (sfxButton.clicked) { // SFX mute
      sfxButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= sfxButton
      this.buttons += sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = true // Mute
      
      menu
    } else if (musicButtonOff.clicked) { // Music on
      musicButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += musicButton
      this.buttons -= musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = false
      if (Sound.inMenu) Sound.playMenuMusic() else Sound.playGameMusic()
      
      menu
    } else if (sfxButtonOff.clicked) { // SFX on
      sfxButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += sfxButton
      this.buttons -= sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = false
      
      menu
    } else {
      menu
    }
  }
}

///////////////
// GAME OVER //
///////////////

object gameOver extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    // Background
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    // Game over text
    var sprite = ImageIO.read(new File("media/UI/game_over_text.png"))
    var location = new Location(236, 190, sprite.getWidth, sprite.getHeight, null)
  })
  
  // Return button
  private var returnButton = new Button(34, 592, ImageIO.read(new File("media/UI/return_button.png")), 
      ImageIO.read(new File("media/UI/return_button_hover.png")))
  
  // Sound buttons
  private var musicButton = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_on.png")))
  private var sfxButton = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_on.png")))
  private var musicButtonOff = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_off.png")))
  private var sfxButtonOff = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_off.png")))
  
  this.buttons = ArrayBuffer[Button](returnButton, musicButton, sfxButton)
  
  this.renderList ++= this.buttons
  
  def run() = {
            
    val scorePanel = new BoxPanel(Orientation.Horizontal)
    scorePanel.contents += new Label("Your score: "+game.points)
    val gameOverFrame = new Frame()
    gameOverFrame.contents =  scorePanel
    gameOverFrame.visible = true
  
    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (returnButton.clicked){ // Return
      returnButton.clicked = false
      
      menu
    } else if (musicButton.clicked) { // Music mute
      musicButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= musicButton
      this.buttons += musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = true
      Sound.stopGameMusic()
      Sound.stopMenuMusic()
      
      menu
    } else if (sfxButton.clicked) { // SFX mute
      sfxButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= sfxButton
      this.buttons += sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = true // Mute
      
      menu
    } else if (musicButtonOff.clicked) { // Music on
      musicButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += musicButton
      this.buttons -= musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = false
      if (Sound.inMenu) Sound.playMenuMusic() else Sound.playGameMusic()
      
      menu
    } else if (sfxButtonOff.clicked) { // SFX on
      sfxButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += sfxButton
      this.buttons -= sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = false
      
      menu
    } else {
      menu
    }
  }
}

//////////////////
// INSTRUCTIONS //
//////////////////

object instructions extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    // Background
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    // Instructions text
    var sprite = ImageIO.read(new File("media/UI/instructions_text.png")) // FIXME images
    var location = new Location(325, 40, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    // Scroll background
    var sprite = ImageIO.read(new File("media/UI/instructions_scroll.png")) // FIXME images
    var location = new Location(254, 136, sprite.getWidth, sprite.getHeight, null)
  })
  
  // Return button
  private var returnButton = new Button(34, 592, ImageIO.read(new File("media/UI/return_button.png")), 
      ImageIO.read(new File("media/UI/return_button_hover.png")))
  
  // Sound buttons
  private var musicButton = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_on.png")))
  private var sfxButton = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_on.png")))
  private var musicButtonOff = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_off.png")))
  private var sfxButtonOff = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_off.png")))
  
  this.buttons = ArrayBuffer[Button](returnButton, musicButton, sfxButton)
  
  this.renderList ++= this.buttons
  
  def run() = {
    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (returnButton.clicked){ // Return
      returnButton.clicked = false
      
      menu
    } else if (musicButton.clicked) { // Music mute
      musicButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= musicButton
      this.buttons += musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = true
      Sound.stopGameMusic()
      Sound.stopMenuMusic()
      
      menu
    } else if (sfxButton.clicked) { // SFX mute
      sfxButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= sfxButton
      this.buttons += sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = true // Mute
      
      menu
    } else if (musicButtonOff.clicked) { // Music on
      musicButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += musicButton
      this.buttons -= musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = false
      if (Sound.inMenu) Sound.playMenuMusic() else Sound.playGameMusic()
      
      menu
    } else if (sfxButtonOff.clicked) { // SFX on
      sfxButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += sfxButton
      this.buttons -= sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = false
      
      menu
    } else {
      menu
    }
  }
}

/////////////////
// HIGH SCORES //
/////////////////

object highscores extends Screen {
  var renderList = ArrayBuffer[C_Drawable](new C_Drawable() {
    // Background
    var sprite = ImageIO.read(new File("media/UI/UI_background.png"))
    var location = new Location(0, 0, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    // Highscores text
    var sprite = ImageIO.read(new File("media/UI/highscores_text.png"))
    var location = new Location(335, 40, sprite.getWidth, sprite.getHeight, null)
  },
  new C_Drawable() {
    // Scroll background
    var sprite = ImageIO.read(new File("media/UI/empty_scroll_big.png"))
    var location = new Location(254, 136, sprite.getWidth, sprite.getHeight, null)
  })
  
  // Return button
  private var returnButton = new Button(34, 592, ImageIO.read(new File("media/UI/return_button.png")), 
      ImageIO.read(new File("media/UI/return_button_hover.png")))
  
  // Sound buttons
  private var musicButton = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_on.png")))
  private var sfxButton = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_on.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_on.png")))
  private var musicButtonOff = new Button(10, 10, ImageIO.read(new File("media/UI/sound_button_music_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_music_off.png")))
  private var sfxButtonOff = new Button(60, 10, ImageIO.read(new File("media/UI/sound_button_sfx_off.png")), 
      ImageIO.read(new File("media/UI/sound_button_sfx_off.png")))
  
  this.buttons = ArrayBuffer[Button](returnButton, musicButton, sfxButton)
  
  this.renderList ++= this.buttons
  
  def run() = {
    
    var scores = highscoreManager.getHighscores(Medium) 
    var panel = new BoxPanel(Orientation.Horizontal)
    for (score <- scores) {
      panel.contents += new Label(score._1 +" "+ score._2 +" "+ score._3)
    }
    val highscoreframe = new Frame()
    highscoreframe.contents = panel 
    highscoreframe.visible = true
    
    while(this.buttons.filter (_.clicked).isEmpty) {
      this.draw(this.MS_PER_UPDATE)
    }
    if (returnButton.clicked){ // Return
      returnButton.clicked = false
      
      menu
    } else if (musicButton.clicked) { // Music mute
      musicButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= musicButton
      this.buttons += musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = true
      Sound.stopGameMusic()
      Sound.stopMenuMusic()
      
      menu
    } else if (sfxButton.clicked) { // SFX mute
      sfxButton.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons -= sfxButton
      this.buttons += sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = true // Mute
      
      menu
    } else if (musicButtonOff.clicked) { // Music on
      musicButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += musicButton
      this.buttons -= musicButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteMusic = false
      if (Sound.inMenu) Sound.playMenuMusic() else Sound.playGameMusic()
      
      menu
    } else if (sfxButtonOff.clicked) { // SFX on
      sfxButtonOff.clicked = false
      // Update lists
      this.renderList --= this.buttons
      this.buttons += sfxButton
      this.buttons -= sfxButtonOff
      this.renderList ++= this.buttons
      // Action
      Sound.muteSfx = false
      
      menu
    } else {
      menu
    }
  }
}