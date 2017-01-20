package game

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 20/01/2017.
  */
class IceShardSpell extends Spell(1.0) {

  var ammo = 40
  val soundEffect = "fireball" // FIXME Wrong sfx

  def projectiles(x:Int, y:Int, world: World, direction: Direction) = {
    ArrayBuffer[Projectile](new IceShard(x, y, world, direction, true, false))
  }
}
