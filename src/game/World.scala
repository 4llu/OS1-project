package game

import scala.collection.mutable.ArrayBuffer
import java.io.File
import javax.imageio.ImageIO

class World {
  
  private val groundTileSet = ImageIO.read(new File("media/ground_tiles.png"))
  private val objectTileSet = ImageIO.read(new File("media/object- layer.png"))
  private val grassImage = groundTileSet.getSubimage(32, 64, 64, 64)
  private val flowersImage = objectTileSet.getSubimage(352, 32, 32, 32)
  private val bushImage = objectTileSet.getSubimage(160, 64, 64, 64)
  val backgroundImage = grassImage
  
  private val map = ArrayBuffer(
    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..ff..........................bb",
    "bb..............................bb",
    "bb..............................bb",    
    "bb..............................bb",
    "bb..............................bb",
    "bb..ff..........................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bb..............................bb",
    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
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
        
        if (tile == '.') {
          tiles(y)(x) = new Tile(grassImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), true, "grass")
          tiles(y)(x+1) = new Tile(null, new Location(0,0,0,0, this), true, "extension")
          tiles(y+1)(x) = new Tile(null, new Location(0,0,0,0, this), true, "extension")
          tiles(y+1)(x+1) = new Tile(null, new Location(0,0,0,0, this), true, "extension")
//          tiles(y)(x+1) = new Tile(grassImage, (x+1)*tileWidth, y*tileHeight, true, "extension")
//          tiles(y+1)(x) = new Tile(grassImage, x*tileWidth, (y+1)*tileHeight, true, "extension")
//          tiles(y+1)(x+1) = new Tile(grassImage, x*tileWidth, (y+1)*tileHeight, true, "extension")
          x += 1
        } else if (tile == 'f') {
          tiles(y)(x) = new Tile(flowersImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth, tileHeight, this), true, "flowers")
        } else if (tile == 'b') {
          tiles(y)(x) = new Tile(bushImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "bush")
          tiles(y)(x+1) = new Tile(null, new Location(0,0,0,0, this), true, "extension")
          tiles(y+1)(x) = new Tile(null, new Location(0,0,0,0, this), true, "extension")
          tiles(y+1)(x+1) = new Tile(null, new Location(0,0,0,0, this), true, "extension")
        }
      }
      x += 1
    }
    y += 1
  }
}
