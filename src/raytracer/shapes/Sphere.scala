package raytracer.shapes

import raytracer.geom._  
import java.awt.Color
import java.io.PrintStream
import raytracer.util.ColorUtil.fromColor

class Sphere(val center: Point3D, val radius: Double, val surface: SurfaceProperty) extends Shape {

	def toCSV = center.toCSV+",\t"+radius+",\t"+surface.toCSV
	
	def distanceAlongRay(ray: Ray) : Option[Double] = {
		// standard quadratic formula
		// A t*t + B t + C = 0

		val centerToRayStart = center → ray.start
		
		val a = ray.direction ⋅ ray.direction
		val b = 2 * (centerToRayStart ⋅ ray.direction)
		val c = (centerToRayStart ⋅ centerToRayStart) - radius * radius
		
		val discriminant = b * b - 4 * a * c; 

		if (discriminant < 0) 
			// there are no intersections
			None
		else {
			val sqRtOfDiscriminant = Math.sqrt(discriminant);
			val q  = if (b >= 0)  
				(-b + sqRtOfDiscriminant)/2.0;
			else 
				(-b - sqRtOfDiscriminant)/2.0;
			
			// compute t0 and t1
			val t0 = q / a;
			val t1 = c / q;
			
			// It's behind you!
			if (t0 < 0) {
				if (t1 < 0) {
					None
				} else {
					Some(t1)
				}
			} else Some(t0)
		}
	}
	
	override def normalAt(p: Point3D): Vector3D = (center → p) normalised
	
	override def toString = "{Sphere "+center+" "+radius+"}"
	
	def canEqual(other: Any) = other.isInstanceOf[Sphere]
	override def equals(other: Any): Boolean =
      other match {
        case that: Sphere =>
          (that canEqual this) &&
          center == that.center &&
          radius == that.radius &&
          surface == that.surface
        case _ => false
      }
}

object Sphere {
	def fromElements(split: Array[String]): Sphere = new Sphere(Point3D.fromElements(split), split(3).toDouble, SurfaceProperty.fromElements(split.drop(4)))
}