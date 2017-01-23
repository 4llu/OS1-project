package game

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class SpellDrop (x: Int, y: Int, world: World,val spell: String) extends Drop(x, y, world) {
  
  val ammoIncrease = if (this.spell == "FirebombSpell") 10 else 20
  var sprite: BufferedImage = ImageIO.read(new File("media/spell_scroll.png"))
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
  
  val collidesWithMonsters = false
  val collidesWithPlayer = false
  
  override def pickedUp(): Unit = {
    super.pickedUp()
    game.player.pickUpSpell(this.spell, this.ammoIncrease)
  }
}