package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 17/01/2017.
  */
class NPC_1(x:Int, y:Int, world:World, direction: Direction) extends Monster(x:Int, y:Int, world:World, 1.0f, direction, 10, 1) {
  var weapon: Weapon = new FireballSpell(); // FIXME Wrong weapon
  var sprite: BufferedImage = ImageIO.read(new File("media/player.png")) // FIXME Wrong sprite
  
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)

  def update(timeElapsed: Long): Unit = {
    // TODO If close enough to attack / not close enough to attack
    if (true) {
      // Attack
    }
    else {
      // Move
    }
  }

  override def move: Unit = {
    // TODO
  }

  override def attack = {
    // TODO
    ArrayBuffer[Projectile]()
  }
}
