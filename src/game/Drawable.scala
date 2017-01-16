package game

import java.awt.image.BufferedImage

/**
  * Created by Allu on 11/11/2016.
  */
trait Drawable extends Locatable {
    var sprite: BufferedImage

    // TODO Sprite list for different actions
}
