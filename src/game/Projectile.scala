package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 16/01/2017.
  */
abstract class Projectile(x:Int, y:Int, world:World) extends C_Locatable with C_Drawable with C_Updatable{
  var direction: Direction
  var speed: Float
  val damage: Int
  var blockedInfo: (Boolean, Option[Monster], Boolean) = _

  def update(timeElapsed: Long): Unit = {
    this.blockedInfo = this.location.moveUntilBlocked(this.direction, this.speed, timeElapsed)
  }
}
