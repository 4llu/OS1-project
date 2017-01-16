package game

/**
  * Created by Allu on 11/11/2016.
  */
class Location(val x: Int, val y: Int, val width: Int, val height: Int, val world: World) {

    def move(dx: Int, dy: Int): Location = {
        new Location(this.x + dx, this.y + dy, this.width, this.height, this.world)
    }

    def isWithin(other: Location, distance: Int): Boolean = {
        val dx = (this.x - other.x).abs - (this.width + other.width)/2
        val dy = (this.y - other.y).abs - (this.height + other.height)/2

        // FIXME (?) Test if this is how it should work
        dx <= distance && dy <= distance
    }
    
  def moveUntilBlocked(dx: Double, dy: Double) = {
    var resultLocation = this
    var blocked = false
    val angle = Math.atan2(dy, dx)
    val distance = Math.sqrt(dx*dx+dy*dy)
    
    var i = 1
    val stepSize = 1
    while (i <= distance/stepSize && !blocked) {
      val newLocation = new Location((this.x + Math.cos(angle)*i*stepSize).toInt, 
          (this.y + Math.sin(angle)*i*stepSize).toInt, this.width, this.height, this.world)
      if (this.world.isWalkable(newLocation)) {
        resultLocation = newLocation
      } else {
        blocked = true
      }
      i += 1
    }
    resultLocation
  }
}
