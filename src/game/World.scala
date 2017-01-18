package game

import scala.collection.mutable.ArrayBuffer
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class World(worldNum: Int) {
  
  private val groundTileSet = ImageIO.read(new File("media/ground_tiles.png"))
  private val objectTileSet = ImageIO.read(new File("media/object_layer.png"))
  private val cliffTileSet = ImageIO.read(new File("media/cliff_tileset.png"))
  private val grassImage = groundTileSet.getSubimage(32, 64, 64, 64)
  private val flowers = objectTileSet.getSubimage(352, 32, 32, 32)
  private val flowersImage = Canvas.combineImages(grassImage, flowers, true)
  private val bush = objectTileSet.getSubimage(160, 64, 64, 64)
  private val bushImage = Canvas.combineImages(grassImage, bush, true)
  // Cliffs sides
  private val cliffDown = cliffTileSet.getSubimage(96, 128, 64, 96)
  private val cliffDownImage = Canvas.combineImages(grassImage, cliffDown, false)
  private val cliffUp = cliffTileSet.getSubimage(96, 0, 64, 64)
  private val cliffUpImage = Canvas.combineImages(grassImage, cliffUp, true)
  private val cliffLeft = cliffTileSet.getSubimage(32, 64, 64, 64)
  private val cliffLeftImage = Canvas.combineImages(grassImage, cliffLeft, true)
  private val cliffRight = cliffTileSet.getSubimage(160, 80, 64, 64)
  private val cliffRightImage = Canvas.combineImages(grassImage, cliffRight, true)
  // Cliff corners
  private val cliffUpLeft = cliffTileSet.getSubimage(32, 0, 64, 96)
  private val cliffUpLeftImage = Canvas.combineImages(grassImage, Canvas.combineImages(grassImage, cliffUpLeft, true), false)
  private val cliffUpRight = cliffTileSet.getSubimage(160, 0, 64, 96)
  private val cliffUpRightImage = Canvas.combineImages(grassImage, cliffUpRight, false)
  private val cliffDownRight = cliffTileSet.getSubimage(160, 128, 64, 96)
  private val cliffDownRightImage = Canvas.combineImages(grassImage, Canvas.combineImages(grassImage, cliffDownRight, true), false)
  private val cliffDownLeft = cliffTileSet.getSubimage(32, 128, 64, 96)
  private val cliffDownLeftImage = Canvas.combineImages(grassImage, cliffDownLeft, false)

  val backgroundImage = grassImage
  
  val map1 = ArrayBuffer(
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

  val map2 = ArrayBuffer(
    "1+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+2+",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.1+U+U+U+U+2+,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++........++........++............................++",
    "L+,.,.,.,.L+,.,.,.,.R+,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++........++........++............................++",
    "L+,.,.,.,.L+,.,.,.,.R+,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++........++........++............................++",
    "L+,.,.,.,.L+,.,.,.,.R+,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++........++........++............................++",
    "L+,.,.,.,.4+D+D+D+D+3+,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++........++++++++++++............................++",
    "L+,.,.,.,.++++++++++++,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "4+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+3+",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++")

  val map = ArrayBuffer(
    "1+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+2+",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++",
    "L+,f,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++......f......f......ff........ff.........f......++",
    "L+,.,f,.,.,f,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+B+B+B+B+B+B+B+B+B+,.,.,f,.,.,.B+B+B+B+B+B+B+B+B+R+",
    "++++++++++++++++++++............++++++++++++++++++++",
    "L+,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++....f.......f...........f...............f.......++",
    "L+,.,.,.,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,f,.,f,.,.R+",
    "++...f.............f........ff.............f......++",
    "L+,.,.,.,f,.,.,.,.,.,.,.,f,.,.,.,.,.,f,.,.,.,.,.,.R+",
    "++...................f............................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,f,.,.R+",
    "++................f..............f................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.R+",
    "++.........................f.........f............++",
    "L+B+B+B+B+B+B+B+B+B+,.,.,.,f,.,.B+B+B+B+B+B+B+B+B+R+",
    "++++++++++++++++++++.....f......++++++++++++++++++++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++.................f..............................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,f,.,.,.R+",
    "++......f....................f............f......++",
    "L+,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++...f.....................f........f.............++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.R+",
    "++................................................++",
    "4+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+3+",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++")


  // Blank grass map
  val map3 = ArrayBuffer(
    "1+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+U+2+",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++",
    "L+,f,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++......f......f......ff........ff.........f......++",
    "L+,.,f,.,.,f,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++................................................++",
    "L+,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++....f.......f...........f...............f.......++",
    "L+,.,.,.,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,f,.,f,.,.R+",
    "++...f.............f........ff.............f......++",
    "L+,.,.,.,f,.,.,.,.,.,.,.,f,.,.,.,.,.,f,.,.,.,.,.,.R+",
    "++...................f............................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,f,.,.R+",
    "++................f..............f................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.R+",
    "++.........................f.........f............++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,.,.,.R+",
    "++.......................f........................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++.................f..............................++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,f,.,.,.R+",
    "++......f....................f............f......++",
    "L+,.,.,.,.,.,.,.,f,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.R+",
    "++...f.....................f........f.............++",
    "L+,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,f,.,.,.,.,.,.R+",
    "++................................................++",
    "4+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+D+3+",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++",
    "++++++++++++++++++++++++++++++++++++++++++++++++++++")
    
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
        } else if (tile == 'D') {
          tiles(y)(x) = new Tile(cliffDownImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffdown")
        } else if (tile == 'U') {
          tiles(y)(x) = new Tile(cliffUpImage,
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffup")
        } else if (tile == 'R') {
          tiles(y)(x) = new Tile(cliffRightImage,
            new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffright")
        } else if (tile == 'L') {
          tiles(y)(x) = new Tile(cliffLeftImage,
            new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffleft")
        } else if (tile == '1') {
          tiles(y)(x) = new Tile(cliffUpLeftImage,
            new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffupleft")
        } else if (tile == '2') {
          tiles(y)(x) = new Tile(cliffUpRightImage,
            new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffupright")
        } else if (tile == '3') {
          tiles(y)(x) = new Tile(cliffDownRightImage,
            new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffdownright")
        } else if (tile == '4') {
          tiles(y)(x) = new Tile(cliffDownLeftImage,
            new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "cliffdownleft")
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
    if (location.x > 0 && location.x+location.width < this.width && location.y > 0 && location.y+location.height < this.height) {
        result = (tiles(location.y/tileHeight)(location.x/tileWidth).walkable) && 
               (tiles(location.y/tileHeight)((location.x+location.width)/tileWidth).walkable) && 
               (tiles((location.y+location.height)/tileHeight)(location.x/tileWidth).walkable) && 
               (tiles((location.y+location.height)/tileHeight)((location.x+location.width)/tileWidth).walkable)
    }
    result
  }
  
}
