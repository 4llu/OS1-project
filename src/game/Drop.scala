package game

abstract class Drop(x: Int, y: Int, world: World) extends C_Updatable {
  
  def update(timeElapsed: Long) {
    // Check picking up
    if (this.location.overlapsWith(game.player.location)) {
      this.pickedUp()
    }
  }
  
  /* Actions to take when the drop is picked up by the player */
  def pickedUp(): Unit = {
    this.remove = true
  }
}