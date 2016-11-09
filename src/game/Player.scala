package game

/**
  * Created by Allu on 10/11/2016.
  */

// TODO Extend updatable and drawable
class Player() extends Creature with Updatable{
    val maxHp = 100
    var hp = this.maxHp
    val maxMana = 100
    var mana = this.maxMana

    def update(): Unit = {

    }
}
