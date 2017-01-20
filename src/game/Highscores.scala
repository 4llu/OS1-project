package game

import java.io.{PrintWriter}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.control.Breaks

/**
  * Created by Allu on 19/01/2017.
  */
object Highscores {

  val highscoreListLength = 5 // Max amount of highscores saved at a time

  /* Return the highscores for the difficulty specified */
  def getHighscores(difficulty: Difficulty): ArrayBuffer[(String, String, Int)] = {
    val scoresSource = Source.fromFile("highscores/" + difficulty.name + ".txt")
    // Read highscores
    // Highscores are in a format of "name;map;score"
    val scores = ArrayBuffer[(String, String, Int)]()
    // Read lines
    for (score <- scoresSource.getLines()) {
      val values = score.split(";")
      scores.append((values(0), values(1), values(2).toInt))
    }
    scoresSource.close()
    scores
  }

  /* Create a new highscore for the difficulty specified. Must be a high enough score for a new highscore. */
  def newHighscore(name: String, difficulty: Difficulty, score: Int, map: String): Unit = {
    // Get current highscores
    var scores = this.getHighscores(difficulty)
    // Determine the position for the new highscore
    var pos = scores.length
    val loop = new Breaks
    loop.breakable {
      for ((highscore, i) <- scores.zipWithIndex) {
        if (score > highscore._3){
          pos = i
          loop.break
        }
      }
    }
    scores.insert(pos, (name, difficulty.name, score))
    if (scores.length > this.highscoreListLength) scores = scores.dropRight(1) // Keep highscore list length to max (this.highscoreListLength)
    // Save new highscorelist
    val pw = new PrintWriter("highscores/" + difficulty.name + ".txt")
    // Write lines
    for (score <- scores) {
      pw.println(score._1 + ";" + score._2 + ";" + score._3)
    }
    pw.close()
  }

  /* Check if a score is high enough to get on to the highscore list on this difficulty */
  def isAHighscore(score: Int, difficulty: Difficulty): Boolean = {
    val scores = this.getHighscores(difficulty)
    var newHighScore = false
    for(highscore <- scores) {
      if (score > highscore._3) newHighScore = true
    }
    // True if the score is bigger than a previoous one or the list is not full yet
    newHighScore || scores.length < this.highscoreListLength
  }
}
