import processing.core._

class Test extends PApplet
{
    override def settings ()
    {
        size(200, 200)
    }

    override def setup() {
        background(0)
        smooth()
    }

    override def draw() {
        stroke(255);
        if (mousePressed) {
            line(mouseX,mouseY,pmouseX,pmouseY);
        }
    }
}

object Test {
    def main(args: Array[String]) {
        PApplet.main(Array[String]("Test"))
    }
}