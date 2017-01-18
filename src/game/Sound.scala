package game

import java.io.{File, FileInputStream, InputStream}
import javax.sound.sampled.AudioSystem

import sun.audio.{AudioPlayer, AudioStream}

/**
  * Created by Aleksanteri on 18/01/2017.
  */
object Sound extends App {

  var menuMusic = "/"
  var in = new FileInputStream(menuMusic)

  var audioStream = new AudioStream(in)
  AudioPlayer.player.start(audioStream);

  // val menuMusic = new File("/media/sounds/Analog-Nostalgia.waw")
  // val menuMusic = new File("/Users/Aleksanteri/Documents/GitHub/OS1-project/media/sounds/Analog-Nostalgia.waw")
  // val audioIn = AudioSystem.getAudioInputStream(menuMusic)
  // println("asd")
  // val clip = AudioSystem.getClip
  // clip.open(audioIn)
  // clip.start()

  // def playGameMusic(): Unit = ???

  // def playMenuMusic(): Unit = ???

}
