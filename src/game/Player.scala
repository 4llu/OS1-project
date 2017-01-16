package game

import scala.collection.mutable.Buffer

/**
  * Created by Allu on 10/11/2016.
  */

import java.io.File
import javax.imageio.ImageIO

class Player(location: Location) extends Creature(location, 100, 1.0f) {
  val maxMana = 100
  var mana = this.maxMana
  val weapons = Buffer(new Fireball())
  var curWeapon = 0
  var weapon: Weapon = null
  this.weapon = this.weapons(this.curWeapon)

  var sprite = ImageIO.read(new File("media/player.png"))
      
  def update(): Unit = {

  }
}
