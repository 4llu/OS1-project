package game

import scala.swing._
import scala.swing.event._
import java.awt.Graphics2D

object Canvas extends Component {
  
  val width = 800
  val height = 600
  preferredSize = new Dimension(width, height)
  
  focusable = true
  listenTo(mouse.clicks)
  listenTo(keys)
  
  reactions += {
    case clickEvent: MouseClicked => 
      game.takeInput("click")
    
    case KeyPressed(_, Key.W, _, _) => 
      game.takeInput("w")
    case KeyPressed(_, Key.A, _, _) => 
      game.takeInput("a")
    case KeyPressed(_, Key.S, _, _) => 
      game.takeInput("s")
    case KeyPressed(_, Key.D, _, _) => 
      game.takeInput("d")
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
      game.player.location.x-game.player.sprite.getWidth/2-game.cameraX,
      game.player.location.y-game.player.sprite.getHeight/2-game.cameraY)
  }
}