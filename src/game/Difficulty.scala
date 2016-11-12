package game

/**
  * Created by Allu on 09/11/2016.
  */
// TODO Add more parameters
sealed abstract class Difficulty(val name: String, val hpMod: Double) {

}

case object Easy extends Difficulty("Easy", 0.8)
case object Medium extends Difficulty("Medium", 1)
case object Hard extends Difficulty("Hard", 1.3)