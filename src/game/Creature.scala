package game

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(var location: Location, val maxHp: Int, val speed: Int) extends Updatable with Drawable {
    var hp = maxHp
    var direction: Direction = South // FIXME Should this be a parameter? (Depends on how monsters are spawned)

    def move(): Unit = {
        this.location.x
    }

    def isDead(): Boolean = this.hp <= 0
}
