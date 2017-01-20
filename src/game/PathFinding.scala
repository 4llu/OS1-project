package game

import util.BinaryHeap
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer
import scala.util.control.Breaks._

object PathFinding {
  val sqrt2 = Math.sqrt(2)

  /** https://en.wikipedia.org/wiki/A*_search_algorithm */
  def aStar(monster:Monster, goal: Cell, world: World):Buffer[Cell] = {
    var pathFound = false
    var openList = new BinaryHeap[Cell]()
    val startCell = world.getCell(monster.centerX, monster.centerY)
    var goalCell = goal
    openList += (startCell, heuristic(startCell, goal))
    var closedList = ArrayBuffer[Cell]()
    val cameFrom = scala.collection.mutable.Map[Cell, Cell]()
    val gScore = scala.collection.mutable.Map[Cell, Double]()
    gScore += startCell -> 0.0
    
    var monsterStuckWithMonster = false
    var monsterStuckWithWall = false
    for (cell <- world.getCellsUnderLocation(
        new Location(monster.location.x - world.cellWidth/2, 
            monster.location.y  - world.cellHeight/2, 
            monster.location.width + world.cellHeight/2, 
            monster.location.height + world.cellHeight/2, world))) {
      if (cell.isBlockedFor(monster)) {
        if (!cell.walkable) monsterStuckWithWall = true
        else monsterStuckWithMonster = true
      }
    }
    while(!openList.isEmpty && !pathFound && closedList.length < 500) {
      val current = openList.root._1
      if (current.creatures.contains(game.player)) {
        pathFound = true
        goalCell = current
      } else {
        openList.delete(current)
        closedList += current
        var i = 0
        for (neighbor <- current.neighbors()) {
          if (!closedList.contains(neighbor)) {
            var cellBlocked = false
            if (!openList.contains(neighbor)) {
              breakable { 
                for (cell <- world.getCellsUnderLocation(
                    new Location(neighbor.centerX-monster.location.width/2 - neighbor.location.width, 
                        neighbor.centerY-monster.location.height/2 - neighbor.location.height, 
                        monster.location.width + neighbor.location.width, 
                        monster.location.height + neighbor.location.height, world))) {
                  if (cell.isBlockedFor(monster) && !cell.creatures.contains(game.player)
                      && ((!cell.walkable && !monsterStuckWithWall) ||
                      (cell.walkable && !monsterStuckWithMonster))) {
                    cellBlocked = true
                    break
                  }
                }
              }
            }
            if (!cellBlocked) {
              var tentative_gScore = gScore.get(current).get
              if ((i <= 4 && i % 2 == 1) || (i > 4 && i % 2 == 0)) { //Check for diagonals
                tentative_gScore += sqrt2
              } else {
                tentative_gScore += 1
              }
              if (!openList.contains(neighbor) || tentative_gScore < gScore.get(neighbor).get) {
                openList += (neighbor, tentative_gScore + heuristic(neighbor, goal))
                gScore += neighbor -> tentative_gScore
                cameFrom += neighbor -> current
              }
            }
          }
          i += 1
        }
      }
    }
    if (pathFound) {
      reconstructPath(cameFrom, goalCell)
    } else {
      Buffer[Cell]()
    }
  }
  
  def heuristic(start: Cell, goal: Cell) = {
    val d_min = Math.min(Math.abs(start.centerX-goal.centerX), Math.abs(start.centerY-goal.centerY))
    val d_max = Math.max(Math.abs(start.centerX-goal.centerX), Math.abs(start.centerY-goal.centerY))
    sqrt2*d_min+d_max-d_min
  }
  
  def reconstructPath(cameFrom: scala.collection.mutable.Map[Cell, Cell], goal: Cell):Buffer[Cell] = {
    var path = Buffer[Cell](goal)
    var current = goal
    while(cameFrom.contains(current)) {
      current = cameFrom.get(current).get
      path += current
    }
    path.reverse
  }
}