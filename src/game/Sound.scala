package game

import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

import scala.collection.mutable.ArrayBuffer
import scala.util.Random;

/**
  * Created by Aleksanteri on 18/01/2017.
  */
object Sound {

  var VOLUME_DB: Float = -10.0f

  val random = new Random()

  val menuMusic: Clip = this.getSound("Analog-Nostalgia")
  val gameMusic: ArrayBuffer[Clip] = ArrayBuffer[Clip]()
  this.gameMusic += this.getSound("Theyre-Closing-In")
  var curGameMusic = this.gameMusic(random.nextInt(this.gameMusic.length))

  def playMenuMusic(): Unit = {
    if (this.curGameMusic.isActive) this.curGameMusic.stop()
    if (!this.menuMusic.isActive) this.menuMusic.loop(Clip.LOOP_CONTINUOUSLY)
  }

  def stopMenuMusic(): Unit = {
    this.menuMusic.stop()
  }

  def playGameMusic(): Unit = {
    if (this.menuMusic.isActive) this.menuMusic.stop()
    if (!this.curGameMusic.isActive) this.curGameMusic.loop(Clip.LOOP_CONTINUOUSLY)
  }

  def stopGameMusic(): Unit = {
    this.curGameMusic.stop()
  }

  def getSound(soundName: String): Clip = {
    val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(new File("media/sounds/" + soundName + ".wav").getAbsoluteFile());
    val clip = AudioSystem.getClip();
    clip.open(audioInputStream);
    clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl].setValue(VOLUME_DB);
    clip
  }
}
