package game

/**
  * Created by Aleksanteri on 15/01/2017.
  */
abstract class Weapon(var ammo: Int) {
  def attack(): Boolean // True if shot, false if not (ammo depleted)
}
