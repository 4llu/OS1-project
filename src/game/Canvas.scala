package game

import scala.swing._
import scala.swing.event._
import java.awt.Graphics2D
import java.awt.image.BufferedImage

object Canvas extends Component {
  
  val width = 800
  val height = 650
  preferredSize = new Dimension(width, height)
  
  focusable = true
//  listenTo(mouse.clicks)
  listenTo(keys)
  
  reactions += {
//    case clickEvent: MouseClicked => 
//      println("click")
      
    case KeyPressed(_, key, _, _) => 
      game.takeInput(key, true)
    case KeyReleased(_, key, _, _) => 
      game.takeInput(key, false)
  }
  
  override def paintComponent(g : Graphics2D) {
//    g.setBackground(new Color(150, 255, 150))
//    g.clearRect(0, 0, size.width, size.height) 
    for (x <- 0 to this.width/game.world.backgroundImage.getWidth) {
      for (y <- 0 to this.height/game.world.backgroundImage.getHeight) { 
        g.drawImage(game.world.backgroundImage, null, x*game.world.backgroundImage.getWidth, y*game.world.backgroundImage.getHeight)
      }
    }
    
    for (tileRow <- game.world.tiles) {
      for (tile <- tileRow) {
        if (tile.tileType != "extension") {
          g.drawImage(tile.sprite, null, tile.location.x-game.cameraX, tile.location.y-game.cameraY)
        }
      }
    }
    
    g.drawImage(game.player.sprite, null, 
        game.player.location.x-game.cameraX, 
        game.player.location.y-game.cameraY)
  }
  
  def combineImages(image1: BufferedImage, image2: BufferedImage) = {
    //Draw image2 on top of image1
    val resultImage = new BufferedImage(Math.min(image1.getWidth, image2.getWidth), 
        Math.min(image1.getHeight, image2.getHeight), BufferedImage.TYPE_INT_ARGB)
    val g = resultImage.getGraphics();
    g.drawImage(image1, 0, 0, null);
    g.drawImage(image2, 0, 0, null);
    resultImage
  }
}