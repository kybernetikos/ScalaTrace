package raytracer.geom

case class Vector3D(x: Double, y: Double, z: Double) {
	def magnitude = math.sqrt(this ⋅ this)
	def | = magnitude
	def -(v: Vector3D) = new Vector3D(x - v.x, y - v.y, z - v.z)
	def *(scale: Double) = new Vector3D(x * scale, y * scale, z * scale)
	def /(scale: Double) = new Vector3D(x / scale, y / scale, z / scale)
	def ⋅(p: Point3D) = x * p.x + y * p.y + z * p.z;
	def ⋅(v: Vector3D) = x * v.x + y * v.y + z * v.z;
	def dot(p: Point3D) = this ⋅ p
	def dot(v: Vector3D) = this ⋅ v 
	def unary_- = this * -1
	
	def normalised = {
		val mag = magnitude
		if (mag == 1) this else this / mag
	}
	def toCSV = x+","+y+","+z
}

object Vector3D {
	val X = Vector3D(1, 0, 0)
	val NEGATIVE_X = -X
	val Y = Vector3D(0, 1, 0)
	val NEGATIVE_Y = -Y
	val Z = Vector3D(0, 0, 1)
	val NEGATIVE_Z = -Z
	def fromElements(elements: Array[String]) = new Vector3D(elements(0).toDouble, elements(1).toDouble, elements(2).toDouble)
}