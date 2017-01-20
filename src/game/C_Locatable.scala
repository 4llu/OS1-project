package game

/**
  * Created by Allu on 11/11/2016.
  */
trait C_Locatable {
    var location: Location
    
    def centerX: Int = this.location.x + this.location.width/2
    def centerY: Int = this.location.y + this.location.height/2
    
    def getDirection(target: C_Locatable): Direction = {
      val dx = target.centerX - this.centerX
      val dy = target.centerY - this.centerY
      
      if (dx > 0 && dy > 0) SouthEast
      else if (dx < 0 && dy > 0) SouthWest
      else if (dx > 0 && dy < 0) NorthEast
      else if (dx < 0 && dy < 0) NorthWest
      else if (dx == 0 && dy < 0) North
      else if (dx == 0 && dy > 0) South
      else if (dx > 0 && dy == 0) East
      else West
    }
}