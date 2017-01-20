package game

import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

/**
  * Created by Allu on 11/11/2016.
  */
class Location(var x: Int, var y: Int, val width: Int, val height: Int, val world: World) {

  def overlapsWith(other: Location) = {
    this.x < other.x + other.width &&
    x + this.width > other.x &&
    y < other.y + other.height &&
    this.y + this.height > other.y
  }
  
  def moveUntilBlocked(direction: Direction, distance: Double, subject: C_Updatable):Boolean = {
    val dx = direction.xStep*distance
    val dy = direction.yStep*distance
    var resultLocation = this
    val dist = Math.sqrt(dx*dx+dy*dy)
    var blocked = false
    
    val stepSize = 0.5
    
    var subjectStuck = false
    for (cell <- world.getCellsUnderLocation(this)) {
      if (cell.isBlockedFor(subject)) {
        subjectStuck = true
      }
    }
    var i = 1
    while (i <= dist/stepSize && !blocked) {
      val newLocation = new Location((this.x + direction.xStep*i*stepSize+0.5).toInt, 
          (this.y + direction.yStep*i*stepSize+0.5).toInt, this.width, this.height, this.world)
      if (!subjectStuck) {
        breakable {
          for (cell <- this.world.getCellsUnderLocation(newLocation)) { 
            if (cell.isBlockedFor(subject) && (subject == game.player ||
                (cell.walkable && cell.creatures.contains(game.player)))) {
              blocked = true
              break
            }
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

  def moveAlongPath(path: Buffer[Cell], distance: Double, subject: C_Updatable) = {
    var i = 0
    var totalDist = 0.0
    var blocked = false
    while (i < path.size && totalDist < distance && !blocked) {
      val dist = Math.hypot(this.x - path(i).centerX, this.y-path(i).centerY)
      if (Math.hypot(this.width/2.0, this.height/2.0) < dist) {
        val moveDistance = Math.min(dist, distance - totalDist)
        blocked = this.moveUntilBlocked(subject.getDirection(path(i)), moveDistance, subject)
        totalDist += moveDistance
      }
      i += 1
    }
    if (i > 0) path.drop(i-1)
    else path
  }
}
