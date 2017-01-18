package game

/**
  * Created by Allu on 09/11/2016.
  */
trait C_Updatable extends C_Drawable{
  def update(timeElapsed: Long): Unit
}
