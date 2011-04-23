package raytracer.shapes

import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import raytracer.geom._
import java.awt.Color

@RunWith(classOf[JUnitRunner])
class PlaneTest extends Spec with MustMatchers {
	describe("A plane") {
		describe("along the y axis at x=1") {
			val origin = Point3D(0, 0, 0)
			val plane = new Plane(-1, Vector3D(1, 0, 0), new SurfaceProperty(0, Color.RED))
			it("intersects a ray fired along the x axis at 9,0,0") {
				val ray = new Ray(origin, Vector3D(1,0,0))
				(plane.distanceAlongRay(ray)) must equal(Some(1.0))
			}
		}
	}
	
}