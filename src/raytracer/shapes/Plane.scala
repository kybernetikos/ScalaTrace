package raytracer.shapes

import raytracer.geom._
import java.awt.Color
import java.io.PrintStream
import raytracer.util.ColorUtil.fromColor

class Plane(val constant: Double, planeNormal: Vector3D, val surface: SurfaceProperty) extends Shape {
	val normal = planeNormal.normalised
	
	def toCSV = constant+",\t"+normal.toCSV+",\t"+surface.toCSV
	
	override def normalAt(p: Point3D): Vector3D = normal

	def distanceAlongRay(ray: Ray) : Option[Double] = {
		val denominator = ray.direction ⋅ normal;
		if (denominator == 0) {
			// Ray and plane are parallel
			None
		} else {
			Some(-(((ray.start ⋅ normal) + constant) / denominator))
		}
	}
}

object Plane {
	def fromElements(split: Array[String]) = new Plane(split(0).toDouble, Vector3D.fromElements(split.drop(1)), SurfaceProperty.fromElements(split.drop(4)))
}