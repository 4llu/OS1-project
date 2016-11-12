package game

/**
  * Created by Allu on 12/11/2016.
  */

/** The class `Direction` represents compass directions in a grid's coordinate system.
  * There are exactly four instances of this class: `North`, `East`, `South` and `West`.
  * These instances are also defined in the same package.
  *
  * All the direction objects are immutable.
  *
  * @param xStep  the change in x coordinate if one moves one step in this direction. For instance, `West` has an `xStep` of -1 and `North` has an `xStep` of 0.
  * @param yStep  the change in y coordinate if one moves one step in this direction. For instance, `North` has an `yStep` of -1 and `West` has an `yStep` of 0. */
sealed abstract class Direction(val xStep: Int, val yStep: Int) {

    /** Returns the next of the four compass directions, moving clockwise from this one.
      * For instance, calling this method on `North` returns `East`. */
    def clockwise = Direction.next(this)


    /** Returns the next of the four compass directions, moving counterclockwise from this
      * one. For instance, calling this method on `North` returns `West`. */
    def counterClockwise = Direction.previous(this)


    /** Returns a textual description of this direction, that is, the English name of the direction. */
    override def toString = this.getClass.getSimpleName.replaceAll("\\$", "")

}

// TODO Lisää väli-ilmansuunnat

/** This immutable singleton object represents the northwardly compass direction. It is one of the four predefined instances of class `Direction`. */
case object North extends Direction( 0,-1)
/** This immutable singleton object represents the eastwardly compass direction. It is one of the four predefined instances of class `Direction`. */
case object East  extends Direction( 1, 0)
/** This immutable singleton object represents the southwardly compass direction. It is one of the four predefined instances of class `Direction`. */
case object South extends Direction( 0, 1)
/** This immutable singleton object represents the westwardly compass direction. It is one of the four predefined instances of class `Direction`. */
case object West  extends Direction(-1, 0)


/** This companion object of class `Direction` provides a couple of useful constants related to directions.
  *
  * NOTE TO STUDENTS: The instance variables of this object represent unchanging general ''constants''.
  * In Scala, it is customary to name such constants with an uppercase initial.
  *
  * @see the class [[Direction]] */
object Direction {

    /** a collection of all the four directions, in clockwise order starting with `North` */
    val Clockwise = Vector[Direction](North, East, South, West)

    /** The number of the compass directions represented by class `Direction`. Four, that is. */
    val Count = Clockwise.size

    private val next = Clockwise.zip(Clockwise.tail ++ Clockwise.init).toMap
    private val previous = this.next.map( _.swap )
}