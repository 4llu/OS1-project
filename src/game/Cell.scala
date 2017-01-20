package game

import scala.collection.mutable.Buffer

class Cell(var location: Location, val walkable: Boolean) extends C_Locatable {  
  def neighbors() = {
    var neighbors = Buffer[Cell]()
    for (dx <- -1 to 1) {
      for (dy <- -1 to 1) {
        if (!(dx == 0 && dy == 0)) {
          neighbors += this.location.world.getCell(
              this.location.x+dx*this.location.width, this.location.y+dy*this.location.height)
        }
      }
    }
    neighbors.toVector
  }
  
  var creatures = Buffer[Creature]()
  var projectiles = Buffer[Projectile]()
}