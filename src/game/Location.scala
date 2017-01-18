package game

/**
  * Created by Allu on 11/11/2016.
  */
class Location(var x: Int, var y: Int, val width: Int, val height: Int, val world: World) {

  def move(dx: Int, dy: Int): Location = {
      new Location(this.x + dx, this.y + dy, this.width, this.height, this.world)
  }
    
  def overlapsWith(other: Location) = {
    ((this.x < other.x && this.x + this.width > other.x) ||
    (other.x < this.x && other.x + other.width > this.x)) &&
    ((this.y < other.y && this.y + this.height > other.y) ||
    (other.y < this.y && other.y + other.height > this.y))
  }
    
  def moveUntilBlocked(direction: Direction, speed: Double, timeElapsed: Long):Boolean = {
    val dx = direction.xStep*speed*timeElapsed
    val dy = direction.yStep*speed*timeElapsed
    var resultLocation = this
    var blocked = false
    val distance = Math.sqrt(dx*dx+dy*dy)
    
    var i = 1
    val stepSize = 1.0
    while (i <= distance/stepSize && !blocked) {
      val newLocation = new Location((this.x + direction.xStep*i*stepSize+0.5).toInt, 
          (this.y + direction.yStep*i*stepSize+0.5).toInt, this.width, this.height, this.world)
      for (monster <- game.monsterList.filter(!_.moving)) {
        if (!this.overlapsWith(monster.location) && newLocation.overlapsWith(monster.location)) blocked = true
      }
      if (this.world.isWalkable(newLocation) && !blocked) {
        resultLocation = newLocation
      } else {
        blocked = true
      }
      i += 1
    }
    this.x = resultLocation.x
    this.y = resultLocation.y
    blocked
  }
}
