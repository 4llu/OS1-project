package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(x: Int, y: Int, world: World, val speed: Double, val maxHp: Int) extends C_Updatable with C_Drawable {
    var hp = maxHp
    var weapon: Weapon

    var sprite: BufferedImage
    var location: Location
    var direction: Direction
    
    val baseSpriteChangeCooldown: Double
    var spriteChangeCooldown:Double = 0
    var spriteIndex = 0
    var spritesByDirection = Map[Direction, Vector[BufferedImage]]()
      
    def centerX: Int = this.location.x + this.sprite.getWidth()/2
    def centerY: Int = this.location.y + this.sprite.getHeight()/2

    def isDead(): Boolean = this.hp <= 0
    
    def walkAnimation(timeElapsed: Long) = {
      if (this.spriteChangeCooldown > 0) {
        this.spriteChangeCooldown -= timeElapsed/1000.0
      } else {
        this.spriteChangeCooldown = this.baseSpriteChangeCooldown
        this.sprite = this.spritesByDirection.get(this.direction).get(spriteIndex)
        this.spriteIndex = (this.spriteIndex + 1)%(spritesByDirection.get(this.direction).get.length)
      }
    }
}
