package game

import java.awt.image.BufferedImage

class Tile(var sprite: BufferedImage, var location: Location, val walkable: Boolean, val tileType: String) extends C_Drawable {

}