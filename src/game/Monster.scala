package game

/**
  * Created by Allu on 11/11/2016.
  */
abstract class Monster(x: Int, y: Int, world: World, maxHp: Int, speed: Int, val points: Int) 
        extends Creature(x: Int, y: Int, world: World, maxHp, speed) {

}
