package raytracer.shapes

import raytracer.geom._
import java.awt.Color
import java.io.PrintStream
import raytracer.util.ColorUtil.fromColor

class AACuboid(val xmin: Double, val xmax: Double, val ymin: Double, val ymax: Double, val zmin: Double, val zmax: Double, val surface: SurfaceProperty) extends Shape {
	
	def toCSV = xmin+","+xmax+","+ymin+","+ymax+","+zmin+","+zmax+",\t"+surface.toCSV
	
	override def normalAt(p: Point3D): Vector3D = {
		val list = List(
				(Math.abs(p.x - xmin), Vector3D.NEGATIVE_X),
				(Math.abs(p.x - xmax), Vector3D.X),
				(Math.abs(p.y - ymin), Vector3D.NEGATIVE_Y),
				(Math.abs(p.y - ymax), Vector3D.Y),
				(Math.abs(p.z - zmin), Vector3D.NEGATIVE_Z),
				(Math.abs(p.z - zmax), Vector3D.Z)
		).sort(_._1 < _._1)
		list(0)._2
	}
		
	def distanceAlongRay(ray: Ray): Option[Double] = {
		def orderTuple(tuple: Tuple2[Double, Double]) = if (tuple._1 > tuple._2) (tuple._2, tuple._1) else tuple 
		
		val (txenter, txexit) = orderTuple((ray.tAtX(this.xmin), ray.tAtX(this.xmax)))
		val (tyenter, tyexit) = orderTuple((ray.tAtY(this.ymin), ray.tAtY(this.ymax)))
		val (tzenter, tzexit) = orderTuple((ray.tAtZ(this.zmin), ray.tAtZ(this.zmax)))

		val maxEnter = Math.max(Math.max(txenter, tyenter), tzenter)
		val minExit = Math.min(Math.min(txexit, tyexit), tzexit)
		
		if (maxEnter <= minExit) {
			Some(maxEnter)
		} else {
			None
		}
	}
}

object AACuboid {
	def fromElements(split: Array[String]) = new AACuboid(split(0).toDouble, split(1).toDouble, split(2).toDouble, split(3).toDouble, split(4).toDouble, split(5).toDouble, SurfaceProperty.fromElements(split.drop(6)))
}