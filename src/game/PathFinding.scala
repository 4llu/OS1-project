package game

import util.BinaryHeap
import scala.collection.mutable.ArrayBuffer

abstract class PathFinding {
//  val sqrt2 = Math.sqrt(2)
//  val directions = Vector[Direction](North, East, South, West, NorthEast, SouthEast, SouthWest, NorthWest)
//  val stepSize = 20.0
//  
//  var pathFound = false
//  def aStar(monster:Monster, goal: Location) = {
//    // https://en.wikipedia.org/wiki/A*_search_algorithm
//    var openList = new BinaryHeap[Location]()
//    openList += (monster.location, heuristic(monster.location, goal))
//    var closedList = ArrayBuffer[Location]()
//    val cameFrom = scala.collection.mutable.Map[Location, Location]()
//    val gScore = scala.collection.mutable.Map[Location, Double]()
//    gScore += monster.location -> 0.0
//    while(!openList.isEmpty) {
//      val current = openList.root._1
//      if (current == goal) {
//        pathFound = true
//      }
//      openList.delete(current)
//      closedList += current
//      var i = 0
//      for (neighbor <- current.neighbors(stepSize)) {
//        if (!closedList.contains(neighbor) && !neighbor.isBlockedForMonster(monster)) {
//          if ((i <= 4 && i % 2 == 1) || (i > 4 && i % 2 == 0)) { //Check for diagonals
//            gScore += neighbor -> (gScore.get(current).get + sqrt2)
//          } else {
//            gScore += neighbor -> (gScore.get(current).get + 1.0)
//          }
//        }
//        i += 1
//      }
//    }
//    
//  }
//  def heuristic(start: Location, goal: Location) = {
//    val d_min = Math.min(Math.abs(start.x-goal.x), Math.abs(start.y-goal.y))
//    val d_max = Math.max(Math.abs(start.x-goal.x), Math.abs(start.y-goal.y))
//    sqrt2*d_min+d_max-d_min
//  }
}