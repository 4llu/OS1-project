package game

import scala.swing._
import scala.swing.event._
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.Icon
import java.awt.GraphicsEnvironment
import javax.swing.JCheckBox

object Canvas extends Component {
  
  val width = 800
  val height = 650
  preferredSize = new Dimension(width, height)
  
  var currentScreen:Screen = menu
  
  focusable = true
  listenTo(mouse.clicks)
  listenTo(mouse.moves)
  listenTo(keys)
  
  reactions += {
    case clickEvent: MouseClicked => 
      currentScreen.recieveClick(clickEvent)
    case moveEvent: MouseMoved =>
      currentScreen.recieveMouseMovement(moveEvent)
      
    case KeyPressed(_, key, _, _) => 
      if (currentScreen == game) {
        game.takeInput(key, true)
      }
    case KeyReleased(_, key, _, _) => 
      if (currentScreen == game) {
        game.takeInput(key, false)
      }
  }
  
  override def paintComponent(g : Graphics2D) {
//    g.setBackground(new Color(150, 255, 150))
//    g.clearRect(0, 0, size.width, size.height) 
      for (renObject <- currentScreen.renderList) {
        g.drawImage(renObject.sprite, null, renObject.location.x-game.cameraX, renObject.location.y-game.cameraY)
      }
      
  }
  
  def combineImages(image1: BufferedImage, image2: BufferedImage, top: Boolean) = {
    //Draw image2 on top of image1
    val resultImage = new BufferedImage(image2.getWidth, image2.getHeight, BufferedImage.TYPE_INT_ARGB)
    val g = resultImage.getGraphics();
    if (top) {
      g.drawImage(image1, 0, 0, null);
      g.drawImage(image2, 0, 0, null);
    } else {
      g.drawImage(image1, image2.getWidth-image1.getWidth, image2.getHeight-image1.getHeight, null);
      g.drawImage(image2, 0, 0, null);
    }
    resultImage
  }
  
}