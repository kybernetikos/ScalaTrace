package raytracer.shapes

import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import raytracer.geom._
import java.awt.Color

@RunWith(classOf[JUnitRunner])
class SphereTest extends Spec with MustMatchers {
	describe("A sphere") {
		describe("of radius 1 at 10,0,0") {
			val sphere = new Sphere(Point3D(10, 0, 0), 1, new SurfaceProperty(0, Color.RED))
			val origin = Point3D(0, 0, 0)
			it("intersects a ray fired along the x axis at 9,0,0") {
				val ray = new Ray(origin, Vector3D(1,0,0))
				(sphere.distanceAlongRay(ray)) must equal(Some(9.0))
			}
			it("doesn't intersect a ray fired along the y axis") {
				val ray = new Ray(origin, Vector3D(0, 1, 0))
				(sphere.distanceAlongRay(ray)) must equal(None)
			}
			it("is equal to another sphere of the same color and radius at the same point") {
				sphere must equal(new Sphere(Point3D(10, 0, 0), 1, new SurfaceProperty(0, Color.RED)))
			}
		}
	}
	
}