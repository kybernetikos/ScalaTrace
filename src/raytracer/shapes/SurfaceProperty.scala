package raytracer.shapes

import java.awt.Color
import raytracer.util.ColorUtil._
import raytracer.util.ColorUtil

case class SurfaceProperty(val shininess: Double, val ambient: Color, val diffuse: Color, val specular: Color) {
	def this(shininess: Double, ambient: Color) = this(shininess, ambient, ambient, Color.DARK_GRAY)
	def toCSV = shininess+", "+ambient.toCSV+", "+diffuse.toCSV+", "+specular.toCSV
}
object SurfaceProperty {
	def fromElements(elements: Array[String]) = new SurfaceProperty(elements(0).toDouble, ColorUtil.fromElements(elements.drop(1)), ColorUtil.fromElements(elements.drop(4)), ColorUtil.fromElements(elements.drop(7)))
}