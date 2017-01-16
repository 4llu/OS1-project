package game

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(var location: Location, val speed: Float, var direction: Direction, val maxHp: Int) extends C_Updatable with C_Drawable {
    var hp = maxHp
    var weapon: Weapon

    def move(): Unit = {
    }

    def isDead(): Boolean = this.hp <= 0
}
