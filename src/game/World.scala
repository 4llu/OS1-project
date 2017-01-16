package game

import scala.collection.mutable.ArrayBuffer
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class World {
  
  private val groundTileSet = ImageIO.read(new File("media/ground_tiles.png"))
  private val objectTileSet = ImageIO.read(new File("media/object- layer.png"))
  private val grassImage = groundTileSet.getSubimage(32, 64, 64, 64)
  private val flowers = objectTileSet.getSubimage(352, 32, 32, 32)
  private val flowersImage = Canvas.combineImages(grassImage, flowers)
  private val bush = objectTileSet.getSubimage(160, 64, 64, 64)
  private val bushImage = Canvas.combineImages(grassImage, bush)
  
  val backgroundImage = grassImage
  
  private val map = ArrayBuffer(
    "BbBbBbBbBbBbBbBbBbBbBbBbBbBbBbBbBb", 
    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",  
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",  // Bb
    "bb..............................bb",  // bb = a bush that covers 4 normal tiles
    "Bb,.ff,.,.,.,.,.,.,.,.,.,.,.,.,.Bb", 
    "bb..,...........................bb",  // ,.
    "Bb,...,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",  // .. = a grass tile that covers 4 normal tiles
    "bb..,...........................bb",
    "Bb,...,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",  // f = flower tile
    "bb..ff..........................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "Bb,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.Bb",
    "bb..............................bb",
    "BbBbBbBbBbBbBbBbBbBbBbBbBbBbBbBbBb",
    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
    
  private val tileWidth = 32
  private val tileHeight = 32
    
  val width = map(0).length*32
  val height = map.size*32
    
  val tileRow: ArrayBuffer[Tile] = 
    collection.mutable.ArrayBuffer.fill(map(0).length())(new Tile(backgroundImage, 
        new Location(0, 0, 0, 0, this), true, "default"))
  var tiles: ArrayBuffer[ArrayBuffer[Tile]] = 
    collection.mutable.ArrayBuffer.fill(map.size)(tileRow.clone())
  var y = 0
  while (y < map.size) {
    var row = map(y)
    var x = 0
    while (x < row.length()) {
      var tile = row(x)
      if (tiles(y)(x).tileType == "default"){
        
        if (tile == ',') { // Top left corner (32x32) of the 64x64 tile
          tiles(y)(x) = new Tile(grassImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), true, "grass")
        } else if (tile == '.') { //The other corners of the 64x64 tile
          tiles(y)(x) = new Tile(null, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), true, "extension")
          
        } else if (tile == 'f') {
          tiles(y)(x) = new Tile(flowersImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth, tileHeight, this), true, "flowers")
          
        } else if (tile == 'B') { // Top left corner (32x32) of the 64x64 tile
          tiles(y)(x) = new Tile(bushImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "bush")
        } else if (tile == 'b') { // The other corners of the 64x64 tile
          tiles(y)(x) = new Tile(bushImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "extension")
        }
      }
      x += 1
    }
    y += 1
  }
  
  def isWalkable(location: Location) = {
    var result = false
    if (location.x > 0 && location.x < this.width && location.y > 0 && location.y < this.height) {
      result = (tiles(location.x/tileWidth)(location.y/tileHeight).walkable) && 
               (tiles((location.x+location.width)/tileWidth)(location.y/tileHeight).walkable) && 
               (tiles(location.x/tileWidth)((location.y+location.height)/tileHeight).walkable) && 
               (tiles((location.x+location.width)/tileWidth)((location.y+location.height)/tileHeight).walkable)
    }
    result
  }
  
}
