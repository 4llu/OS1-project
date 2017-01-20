package game

abstract class Drop(x: Int, y: Int, world: World) extends C_Updatable {
  
  def update(timeElapsed: Long) {
    if (this.location.overlapsWith(game.player.location)) {
      this.pickedUp()
    }
  }
  
  /* Actions to take when the Drop is picked up*/
  def pickedUp(): Unit
}