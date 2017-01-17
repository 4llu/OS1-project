package game
/**
  * Created by Allu on 12/11/2016.
  */

sealed abstract class Direction(val xStep: Double, val yStep: Double) {}

case object North extends Direction(0, -1)
case object East  extends Direction(1, 0)
case object South extends Direction(0, 1)
case object West  extends Direction(-1, 0)
case object NorthWest extends Direction(-1.0/Math.sqrt(2.0), -1.0/Math.sqrt(2.0))
case object NorthEast  extends Direction(1.0/Math.sqrt(2.0), -1.0/Math.sqrt(2.0))
case object SouthWest extends Direction(-1.0/Math.sqrt(2.0), 1.0/Math.sqrt(2.0))
case object SouthEast  extends Direction(1.0/Math.sqrt(2.0), 1.0/Math.sqrt(2.0))