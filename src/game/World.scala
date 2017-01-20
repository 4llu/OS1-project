package game

import scala.collection.mutable.ArrayBuffer
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.io.Source

class World(worldNum: Int) {
  // Load tile sprites

  private val groundTileSet = ImageIO.read(new File("media/ground_tiles.png"))
  private val objectTileSet = ImageIO.read(new File("media/object_layer.png"))
  private val cliffTileSet = ImageIO.read(new File("media/cliff_tileset.png"))
  private val grassImage = groundTileSet.getSubimage(32, 64, 64, 64)
  private val flowers = objectTileSet.getSubimage(352, 32, 32, 32)
  private val flowersImage = Canvas.combineImages(grassImage, flowers, true)
  private val bush = objectTileSet.getSubimage(160, 64, 64, 64)
  private val bushImage = Canvas.combineImages(grassImage, bush, true)
  // Cliff sides
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

  // Read map from text file
  val mapSource = Source.fromFile("maps/map" + worldNum + ".txt")
  val map = ArrayBuffer[String]()
  for (line <- mapSource.getLines()) {
    map += line
  }
  mapSource.close()
    
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
        
        if (tile == ',') {
          tiles(y)(x) = new Tile(grassImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), true, "grass")
        } else if (tile == '.') {
          tiles(y)(x) = new Tile(null, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), true, "extension")
          
        } else if (tile == 'f') {
          tiles(y)(x) = new Tile(flowersImage, 
              new Location(x*tileWidth, y*tileHeight, tileWidth, tileHeight, this), true, "flowers")
          
        } else if (tile == 'B') {
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
        } else if (tile == '+') {
          tiles(y)(x) = new Tile(null, 
              new Location(x*tileWidth, y*tileHeight, tileWidth*2, tileHeight*2, this), false, "extension")
        }
      }
      x += 1
    }
    y += 1
  }
  
  val cellWidth = 16
  val cellHeight = 16
  val grid = ArrayBuffer[ArrayBuffer[Cell]]()
  for (y <- 0 until tiles.size) {
    for (j <- 0 until tileHeight / cellHeight) {
      val gridRow = ArrayBuffer[Cell]()
      for (x <- 0 until tiles(0).size) {
        for (i <- 0 until tileWidth / cellWidth) {
          val cellLocation = new Location(tiles(y)(x).location.x+i*cellWidth, 
              tiles(y)(x).location.y+j*cellHeight, cellWidth, cellHeight, this)
          gridRow += new Cell(cellLocation, tiles(y)(x).walkable)
        }
      }
      grid += gridRow
    }
  }
  
  def getCell(x: Int, y: Int) = {
    if (x < 0 || y < 0 || x >= this.width || y >= this.height) new Cell(new Location(0,0,0,0,this), false)
    else grid(y*this.grid.size/this.height)(x*grid(0).length/this.width)
  }
  
  def getCellsUnderLocation(location: Location) = {
    val cells = ArrayBuffer[Cell]()
    for (y <- location.y until location.y + location.height+this.cellHeight by this.cellHeight) {
      for (x <- location.x until location.x + location.width+this.cellWidth by this.cellWidth) {
        cells += this.getCell(x, y)
      }
    }
    cells
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
