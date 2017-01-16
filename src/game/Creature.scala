package game

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(var location: Location, val maxHp: Int, val speed: Float) extends Updatable with Drawable {
    var hp = maxHp
    var direction: Direction = South // FIXME Should this be a parameter? (Depends on how monsters are spawned)
    var weapon: Weapon

    def move(): Unit = {
    }

    def isDead(): Boolean = this.hp <= 0
}
