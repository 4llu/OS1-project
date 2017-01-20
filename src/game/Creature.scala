package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 10/11/2016.
  */
abstract class Creature(x: Int, y: Int, world: World, val speed: Double, val maxHp: Int) extends C_Updatable with C_Drawable {
  // Stats
  var hp = maxHp
  var weapon: Spell

  var sprite: BufferedImage
  var location: Location
  var direction: Direction

  // Sprite animation config
  protected val baseSpriteChangeCooldown: Double
  protected var spriteChangeCooldown:Double = 0
  protected var spriteIndex = 0
  protected var spritesByDirection = Map[Direction, Vector[BufferedImage]]()
    
//  def centerX: Int = this.location.x + this.sprite.getWidth()/2
//  def centerY: Int = this.location.y + this.sprite.getHeight()/2

  def isDead(): Boolean = this.hp <= 0
  
  def takeDamage(damage: Int): Unit = this.hp -= damage

  protected def walkAnimation(timeElapsed: Long) = {
    if (this.spriteChangeCooldown > 0) {
      this.spriteChangeCooldown -= timeElapsed/1000.0
    } else {
      this.spriteChangeCooldown = this.baseSpriteChangeCooldown
      this.sprite = this.spritesByDirection.get(this.direction).get(spriteIndex)
      this.spriteIndex = (this.spriteIndex + 1)%(spritesByDirection.get(this.direction).get.length)
    }
  }
}
