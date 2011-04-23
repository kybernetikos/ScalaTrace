package raytracer.light

import raytracer.geom._

class PositionalLight(val source: Point3D, val intensity: Double) extends Light {
	def distanceFrom(p: Point3D): Double = (source - p) magnitude
	def directionFrom(p: Point3D): Vector3D = (p → source) normalised
	def toCSV = source.toCSV+",\t"+intensity
}
object PositionalLight {
	def fromElements(split: Array[String]) = new PositionalLight(Point3D(split(0).toDouble, split(1).toDouble, split(2).toDouble), split(3).toDouble)
}