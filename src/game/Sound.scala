package game

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import sun.audio.{AudioPlayer, AudioStream}

/**
  * Created by Aleksanteri on 18/01/2017.
  */
object Sound extends App {

  var VOLUME_DB = -10.0f
  val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(new File("media/sounds/Analog-Nostalgia.wav").getAbsoluteFile());
  val clip = AudioSystem.getClip();
  clip.open(audioInputStream);
  clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl].setValue(VOLUME_DB);
  clip.start();
}
