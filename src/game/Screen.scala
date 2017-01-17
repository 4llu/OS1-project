package game

import scala.collection.mutable.ArrayBuffer

abstract class Screen() {
  var renderList: ArrayBuffer[C_Drawable]
  def run(): Int
}