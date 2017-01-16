package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(x: Int, y: Int, world: World, val speed: Float, var direction: Direction, val maxHp: Int) extends C_Updatable with C_Drawable {
    var hp = maxHp
    var weapon: Weapon

    var sprite: BufferedImage
    
    var location: Location
    
    def move(): Unit = {
    }

    def isDead(): Boolean = this.hp <= 0
}
