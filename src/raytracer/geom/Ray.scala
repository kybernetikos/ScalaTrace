package raytracer.geom

class Ray(st: Point3D, dir: Vector3D ) {
	val start = st
	val direction = dir
	def apply(t: Double) = start + (direction * t)

	def tAtX(x: Double): Double = (x - start.x) / direction.x
	def tAtY(y: Double): Double = (y - start.y) / direction.y
	def tAtZ(z: Double): Double = (z - start.z) / direction.z
	
	def pointAtX(x: Double) = {
		val t = (x - start.x) / direction.x;
		if (direction.x == 0) None else Some(this(t))
	}
	def pointAtY(y: Double) = {
		val t = (y - start.y) / direction.y;
		if (direction.y == 0) None else Some(this(t))
	}
	def pointAtZ(z: Double) = {
		val t = (z - start.z) / direction.z;
		if (direction.z == 0) None else Some(this(t))
	}
	override def toString = "{Ray "+start+" "+direction+"}"
	
	def reflect(p: Point3D, n: Vector3D): Ray = new Ray(p, direction - n * 2 * (direction ⋅ n))
}

object Ray {
	def apply(start: Point3D, direction: Vector3D) = new Ray(start, direction.normalised)
	def unapply(ray: Ray) = Some((ray.start, ray.direction))
}