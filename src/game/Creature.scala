package game

/**
  * Created by Allu on 10/11/2016.
  */
trait Creature {
    val maxHp: Int
    var hp: Int

    def isDead(): Boolean = this.hp <= 0
}
