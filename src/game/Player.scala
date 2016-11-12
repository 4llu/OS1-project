package game

/**
  * Created by Allu on 10/11/2016.
  */

// FIXME Creature speed is a completely made up number atm
class Player(location: Location) extends Creature(location, 100, 10) with Updatable with Drawable {
    val maxMana = 100
    var mana = this.maxMana

    def update(): Unit = {

    }
}
