package raytracer.shapes
 import java.awt.Color
import java.io.PrintStream
import raytracer.geom._

trait Shape {
	def surface : SurfaceProperty
	def toCSV: String
	def distanceAlongRay(ray: Ray): Option[Double]
	def normalAt(point: Point3D): Vector3D
}