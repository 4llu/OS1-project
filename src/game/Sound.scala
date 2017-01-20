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

  val random = new Random()

  // Mute options
  val muteSfx = false
  val muteMusic = false

  // Preload musics
  val menuMusic: Clip = this.getSound("menu_music", -10.0f)
  val gameMusic: Clip = this.getSound("game_music_1", -10.0f)

  /* Start menu music if not playing yet. Also stop game music if it's playing. */
  def playMenuMusic(): Unit = {
    if (!this.muteMusic) {
      this.stopGameMusic()
      if (!this.menuMusic.isActive) this.menuMusic.loop(Clip.LOOP_CONTINUOUSLY)
    }
  }

  def stopMenuMusic(): Unit = {
    if (this.menuMusic.isActive) this.menuMusic.stop()
  }

  /* Start game music if not playing yet. Also stop menu music if it's playing. */
  def playGameMusic(): Unit = {
    if (!this.muteMusic) {
      this.stopMenuMusic()
      if (!this.gameMusic.isActive) this.gameMusic.loop(Clip.LOOP_CONTINUOUSLY)
    }
  }

  def stopGameMusic(): Unit = {
    if (this.gameMusic.isActive) this.gameMusic.stop()
  }

  /* Play SFX with the given name. Name must be the same as the SFX filename without the filename extension */
  def playSoundEffect(name: String): Unit = {
    if (!this.muteSfx) this.getSound("sfx/" + name, -20.0f).start()
  }

  /* Get a sound clip by name */
  def getSound(soundName: String, volumeDB: Float): Clip = {
    val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(new File("media/sounds/" + soundName + ".wav").getAbsoluteFile());
    val clip = AudioSystem.getClip();
    clip.open(audioInputStream);
    clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl].setValue(volumeDB);
    clip
  }
}
