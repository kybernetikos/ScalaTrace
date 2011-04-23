package raytracer.light

import raytracer.geom._

trait Light {
	def distanceFrom(p: Point3D): Double
	def directionFrom(p: Point3D): Vector3D
	def intensity: Double
	def toCSV: String
}