package game

//import scala.swing._
import swing._
import java.awt.{Color, Graphics2D, Dimension}
import java.awt.event._

/**
  * Created by Allu on 04/11/2016.
  */
object main extends SimpleSwingApplication {
    //  var panel = new DataPanel {
    //preferredSize = new Dimension(800, 600)
    //}

  def top = new MainFrame {

      title = "Hello World"
      contents = new Button {
          text = "Click Me!"
      }
 //   this.peer.addKeyListener(new KeyListener() {
      //       def keyPressed(e:KeyEvent) {
      //  println("key pressed: "+e.getKeyChar)
      //  if (e.getKeyChar.toLower.equals('w')) {
      //    cameraY -= 50
      //  } else if (e.getKeyChar.toLower.equals('a')) {
      //    cameraX -= 50
      //  } else if (e.getKeyChar.toLower.equals('s')) {
      //    cameraY += 50
      //  } else if (e.getKeyChar.toLower.equals('d')) {
      //    cameraX += 50
      //  }
      //}

      //def keyReleased(e:KeyEvent) {
      //  println("key released: "+e.getKeyChar)
      //}

      //  def keyTyped(e:KeyEvent) {
      //  println("key typed: "+e.getKeyChar)
      //}
      //})
      //contents = new BoxPanel(Orientation.NoOrientation) {
      //contents += panel
      //}
  }
    game.start(1, Hard)
}

class DataPanel extends Panel {
  override def paintComponent(g: Graphics2D) {
    g.clearRect(0, 0, size.width, size.height)
  }
}