package game

import scala.collection.mutable.ArrayBuffer
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class World(worldNum: Int) {
  
  private val groundTileSet = ImageIO.read(new File("media/ground_tiles.png"))
  private val objectTileSet = ImageIO.read(new File("media/object- layer.png"))
  private val cliffTileSet = ImageIO.read(new File("media/Cliff_tileset.png"))
  private val grassImage = groundTileSet.getSubimage(32, 64, 64, 64)
  private val flowers = objectTileSet.getSubimage(352, 32, 32, 32)
  private val flowersImage = Canvas.combineImages(grassImage, flowers, true)
  private val bush = objectTileSet.getSubimage(160, 64, 64, 64)
  private val bushImage = Canvas.combineImages(grassImage, bush, true)
  private val cliffDown = cliffTileSet.getSubimage(96, 128, 64, 96)
  private val cliffDownImage = Canvas.combineImages(grassImage, cliffDown, false)
  private val cliffUp = cliffTileSet.getSubimage(96, 0, 64, 64)
  private val cliffUpImage = Canvas.combineImages(grassImage, cliffUp, true)
  
  val backgroundImage = grassImage
  
  private val map = ArrayBuffer(  
    "U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+",  
    "++++++++++++++++++++++++++++++++++",  
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",  // B+
    "++..............................++",  // ++ = a bush that covers 4 normal tiles
    "B+,.ff,.,.,.,.,.,.,.,.,.,.,.,.,.B+", 
    "++..,...........................++",  // ,.
    "B+,...,.,.,.,.,.,.,.,.,.,.,.,.,.B+",  // .. = a grass tile that covers 4 normal tiles
    "++..,...........................++",
    "B+,...,.,.,.,.,.,.,.,.,.,.,.,.,.B+",  // f = flower tile
    "++..ff..........................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "B+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.B+",
    "++..............................++",
    "D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+", 
    "++++++++++++++++++++++++++++++++++", 
    "++++++++++++++++++++++++++++++++++")
    
  private val tileWidth = 32
  private val tileHeight = 32
    
  val width = map(0).length*32
  val height = map.size*32
  
//  var tiles = ArrayBuffer[ArrayBuffer[Tile]]()
//  for (y <- 0 to map.size + 2*mapBackgroundHeight) {
//    val tileRow = ArrayBuffer[Tile]()
//    for (x <- 0 to map(0).length() + 2*mapBackgroundWidth) {
//      tileRow += new Tile(backgroundImage, new Location(x*tileWidth, y*tileHeight, tileWidth, tileHeight, this), true, "default")
//      
//    }
//    tiles += tileRow
//  }
  
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
        } else if (tile == 'D') {
          tiles(y)(x) = new Tile(cliffDownImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffdown")
        } else if (tile == 'U') {
          tiles(y)(x) = new Tile(cliffUpImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffdown")
        } else if (tile == '+') { // The other corners of the 64x64 tile
          tiles(y)(x) = new Tile(null, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "extension")
        }
      }
      x += 1
    }
    y += 1
  }
  
  val bgTileWidth = backgroundImage.getWidth
  val bgTileHeight = backgroundImage.getHeight
  val mapBackgroundWidth = Canvas.width/2
  val mapBackgroundHeight = Canvas.height/2
  
  val backgroundTiles = ArrayBuffer[ArrayBuffer[Tile]]()
    
  for (y <- -mapBackgroundWidth to 0 by bgTileHeight) {
    val tileRow = ArrayBuffer[Tile]()
    for (x <- -mapBackgroundWidth to width + mapBackgroundWidth by bgTileWidth) {
      tileRow += new Tile(backgroundImage, new Location(x, y, bgTileWidth, bgTileHeight, this), false, "background")
    }
    backgroundTiles += tileRow
  } 
  for (y <- height to height+mapBackgroundHeight by bgTileHeight) {
    val tileRow = ArrayBuffer[Tile]()
    for (x <- -mapBackgroundWidth to width + mapBackgroundWidth by bgTileWidth) {
      tileRow += new Tile(backgroundImage, new Location(x, y, bgTileWidth, bgTileHeight, this), false, "background")
    }
    backgroundTiles += tileRow
  }
  for (y <- 0 to height by bgTileHeight) {
    val tileRow = ArrayBuffer[Tile]()
    for (x <- -mapBackgroundWidth to 0 by bgTileWidth) {
      tileRow += new Tile(backgroundImage, new Location(x, y, bgTileWidth, bgTileHeight, this), false, "background")
    }
    backgroundTiles += tileRow
  }
  for (y <- 0 to height by bgTileHeight) {
    val tileRow = ArrayBuffer[Tile]()
    for (x <- width to width + mapBackgroundWidth by bgTileWidth) {
      tileRow += new Tile(backgroundImage, new Location(x, y, bgTileWidth, bgTileHeight, this), false, "background")
    }
    backgroundTiles += tileRow
  }
  
  def isWalkable(location: Location) = {
    var result = false
    if (location.x > 0 && location.x < this.width && location.y > 0 && location.y < this.height) {
      try {
        result = (tiles(location.x/tileWidth)(location.y/tileHeight).walkable) && 
               (tiles((location.x+location.width)/tileWidth)(location.y/tileHeight).walkable) && 
               (tiles(location.x/tileWidth)((location.y+location.height)/tileHeight).walkable) && 
               (tiles((location.x+location.width)/tileWidth)((location.y+location.height)/tileHeight).walkable)
      } catch {
        case e: Exception => result = false
      }
    }
    result
  }
  
}
