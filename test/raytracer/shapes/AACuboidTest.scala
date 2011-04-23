package raytracer.shapes

import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import raytracer.geom._
import java.awt.Color

@RunWith(classOf[JUnitRunner])
class AACuboidTest extends Spec with MustMatchers {
	describe("An Axis Aligned Cuboid") {
		describe("at 1,0,0") {
			val aa = new AACuboid(1, 2, -1, 1, -1, 1, new SurfaceProperty(0, Color.RED))
			val origin = Point3D(0, 0, 0)
			it("intersects a ray fired along the x axis at 1,0,0") {
				val ray = new Ray(origin, Vector3D(1,0,0))
				(aa.distanceAlongRay(ray)) must equal(Some(1.0))
			}
		}
	}
	
}