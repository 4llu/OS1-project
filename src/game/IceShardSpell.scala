package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 20/01/2017.
  */
class IceShardSpell extends Spell(0.5) {

  var ammo = 30
  val soundEffect = "icespell_sound"

  def projectiles(x:Int, y:Int, world: World, direction: Direction) = {
    ArrayBuffer[Projectile](new IceShard(x, y, world, direction, true, false))
  }
  
  override def toString: String = "IceShardSpell"
}
