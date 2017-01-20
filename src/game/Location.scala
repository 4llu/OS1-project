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
  
  def moveUntilBlocked(direction: Direction, speed: Double, timeElapsed: Long, subject: C_Updatable):Boolean = {
//  /* Move until blocked by the terrain or by a monster (default, monsters can be ignored with an extra parameter */
//  def moveUntilBlocked(direction: Direction, speed: Double, timeElapsed: Long, ignoreMonsters: Boolean = false):(Boolean, Option[Monster], Boolean) = {
    val dx = direction.xStep*speed*timeElapsed
    val dy = direction.yStep*speed*timeElapsed
    var resultLocation = this
    val distance = Math.sqrt(dx*dx+dy*dy)
    var blocked = false
    
    // Different options of being blocked
//    var blockingCreature: Option[Monster] = None
//    var blockedByTile = false
//
//    // For convenience
//    def blocked: Boolean = blockingCreature.isDefined || blockedByTile
    
    var i = 1
    val stepSize = 0.2
    while (i <= distance/stepSize && !blocked) {
      val newLocation = new Location((this.x + direction.xStep*i*stepSize+0.5).toInt, 
          (this.y + direction.yStep*i*stepSize+0.5).toInt, this.width, this.height, this.world)
      breakable {
        for (cell <- this.world.getCellsUnderLocation(newLocation)) {
          if (!cell.walkable || (!cell.creatures.contains(subject) &&
              (subject.collidesWithPlayer && cell.creatures.contains(game.player)) ||
              (subject.collidesWithMonsters && !cell.creatures.filter(_ != subject).filter(_ != game.player).isEmpty))) {
            blocked = true
            break
          }
        }
      // Blocked by a monster (if not ignored)
//      if (!ignoreMonsters) {
//        for (monster <- game.monsterList.filter(!_.moving)) {
//          if (!this.overlapsWith(monster.location) && newLocation.overlapsWith(monster.location)){
//            blockingCreature = Some(monster)
//            }
//          }
//        }
//      // Blocked by a tile
//      if (this.world.isWalkable(newLocation) && !blocked) {
//        resultLocation = newLocation
//      } else {
//        blockedByTile = true
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
//    (blocked, blockingCreature, blockedByTile)
  }
}
