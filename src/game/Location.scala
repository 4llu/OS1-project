package game

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
    
  def moveUntilBlocked(direction: Direction, speed: Double, timeElapsed: Long):(Boolean, Option[Monster], Boolean) = {
    val dx = direction.xStep*speed*timeElapsed
    val dy = direction.yStep*speed*timeElapsed
    var resultLocation = this
    val distance = Math.sqrt(dx*dx+dy*dy)
    // Different options of being blocked
    var blockingCreature: Option[Monster] = None
    var blockedByTile = false

    // For convenience
    def blocked: Boolean = blockingCreature.isDefined || blockedByTile
    
    var i = 1
    val stepSize = 1.0
    while (i <= distance/stepSize && !blocked) {
      val newLocation = new Location((this.x + direction.xStep*i*stepSize+0.5).toInt, 
          (this.y + direction.yStep*i*stepSize+0.5).toInt, this.width, this.height, this.world)
      // Blocked by a monster
      for (monster <- game.monsterList.filter(!_.moving)) {
        if (!this.overlapsWith(monster.location) && newLocation.overlapsWith(monster.location)){
          blockingCreature = Some(monster)
        }
      }
      // Blocked by a tile
      if (this.world.isWalkable(newLocation) && !blocked) {
        resultLocation = newLocation
      } else {
        blockedByTile = true
      }
      i += 1
    }
    this.x = resultLocation.x
    this.y = resultLocation.y
    (blocked, blockingCreature, blockedByTile)
  }
}
