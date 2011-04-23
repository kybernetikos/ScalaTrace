package raytracer.geom

case class Point3D(x: Double, y: Double, z: Double) {
	def -(that: Point3D) = new Vector3D(x - that.x, y - that.y, z - that.z)
	def -(that: Double) = new Vector3D(x - that, y - that, z - that)
	def →(that: Point3D) = that - this
	
	def +(that: Vector3D) = new Point3D(x + that.x, y + that.y, z + that.z)
	
	def ⋅(p: Point3D) = x * p.x + y * p.y + z * p.z;
	def ⋅(v: Vector3D) = x * v.x + y * v.y + z * v.z;
	
	def toCSV = x+","+y+","+z
}

object Point3D {
	def fromElements(elements: Array[String]) = new Point3D(elements(0).toDouble, elements(1).toDouble, elements(2).toDouble)
}