package game

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Allu on 20/01/2017.
  */
class IceShard(x:Int, y:Int, world:World, var direction: Direction, hitsMonsters: Boolean, hitsPlayer: Boolean) 
  extends Projectile(x, y, world, hitsMonsters, hitsPlayer) {
  var speed = 1.2f
  val damage = 25
  var sprite: BufferedImage = ImageIO.read(new File("media/freezeball.png"))
  var location = new Location(x, y, sprite.getWidth, sprite.getHeight, world)
}