package raytracer

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import raytracer.geom._
import raytracer.shapes._
import java.io.FileOutputStream
import raytracer.light._
import swing._

object RayTracer {
	def main(args: Array[String]) {
		val scene = new Scene()
		scene.read("Scene.txt")
		scene.write("Out.txt")

		val eye = Point3D(20, 0, -90)
		val img = scene.render(eye, 200, 200)
		
		ImageIO.write(img, "png", new FileOutputStream("image.png"))
		
		val frame = new MainFrame {
			title = "Render"
			contents = new Component {
				preferredSize = new Dimension(img.getWidth, img.getHeight)
				minimumSize = preferredSize
				maximumSize = preferredSize
				override def paintComponent(g: Graphics2D) {
					g.drawImage(img, null, null)
				}
			}
		}
		frame.visible = true
	}
}