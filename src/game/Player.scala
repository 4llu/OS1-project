package game

import scala.collection.mutable.{ArrayBuffer, Buffer}

/**
  * Created by Allu on 10/11/2016.
  */

import java.io.File
import javax.imageio.ImageIO

class Player(location: Location) extends Creature(location, 1.0f, South, 100) {
  val maxMana = 100
  var mana = this.maxMana
  val weapons = Buffer(new FireballSpell())
  var curWeapon = 0
  var weapon: Weapon = null
  this.weapon = this.weapons(this.curWeapon)

  var sprite = ImageIO.read(new File("media/player.png"))
      
  def update(timeElapsed: Long): Unit = {

  }

  def attack(): ArrayBuffer[Projectile] = {
    this.weapon.fire(this.location, this.direction)
  }
}
