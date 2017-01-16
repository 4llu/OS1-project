package game

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(location: Location, speed: Int, direction: Direction, maxHp: Int, val points: Int) extends Creature(location, speed, direction, maxHp) {

}
