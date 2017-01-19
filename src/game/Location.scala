package game

import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

/**
  * Created by Allu on 11/11/2016.
  */
class Location(var x: Int, var y: Int, val width: Int, val height: Int, val world: World) {

  def move(dx: Int, dy: Int): Location = {
      new Location(this.x + dx, this.y + dy, this.width, this.height, this.world)
  }
    
  def overlapsWith(other: Location) = {
    this.x < other.x + other.width &&
    x + this.width > other.x &&
    y < other.y + other.height &&
    this.y + this.height > other.y
  }
    
  def moveUntilBlocked(direction: Direction, speed: Double, timeElapsed: Long, subject: C_Locatable):Boolean = {
    val dx = direction.xStep*speed*timeElapsed
    val dy = direction.yStep*speed*timeElapsed
    var resultLocation = this
    var blocked = false
    val distance = Math.sqrt(dx*dx+dy*dy)
    
    var i = 1
    val stepSize = 0.2
    while (i <= distance/stepSize && !blocked) {
      val newLocation = new Location((this.x + direction.xStep*i*stepSize+0.5).toInt, 
          (this.y + direction.yStep*i*stepSize+0.5).toInt, this.width, this.height, this.world)
      breakable {
        for (cell <- this.world.getCellsUnderLocation(newLocation)) {
          if ((!cell.walkable || !cell.creatures.filter(_ != subject).isEmpty) && !cell.creatures.contains(subject)) {
            blocked = true
            break
          }
        }
      }
      if (!blocked) resultLocation = newLocation
      i += 1
    }
    for (cell <- this.world.getCellsUnderLocation(this)) {
      cell.creatures = cell.creatures.filter(_ != subject)
      cell.projectiles = cell.projectiles.filter(_ != subject)
    }
    this.x = resultLocation.x
    this.y = resultLocation.y
    blocked
  }
}
