package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(x: Int, y: Int, world: World, val maxHp: Int, val speed: Float) extends C_Updatable with C_Drawable {
    var hp = maxHp
    var direction: Direction = South // FIXME Should this be a parameter? (Depends on how monsters are spawned)
    var weapon: Weapon
    
    var sprite: BufferedImage
    
    var location: Location
    
    def move(): Unit = {
    }

    def isDead(): Boolean = this.hp <= 0
}
