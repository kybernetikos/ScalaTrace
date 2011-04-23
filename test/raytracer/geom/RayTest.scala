package raytracer.geom
import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RayTest extends Spec with MustMatchers {

	describe("A ray") {
		describe("when started at 0,0,0 and traveling along the +x axis") {
			val ray = new Ray(Point3D(0, 0, 0), Vector3D(1, 0, 0))
			
			it("must travel along the -x axis when reflected back along itself") {
				val normal = Vector3D(-1, 0, 0)
				val reflection = ray.reflect(Point3D(1, 0, 0), normal)
				reflection.direction.x must equal(-1.0)
				reflection.direction.y must equal(0.0)
				reflection.direction.z must equal(0.0)
				reflection.start must equal(Point3D(1, 0, 0))
			}
			it("must travel parallel to the y axis when reflected at 45 degrees") {
				val normal = Vector3D(-1, 1, 0).normalised
				val reflection = ray.reflect(Point3D(1, 0, 0), normal)
				reflection.direction.x must be (0.0 plusOrMinus 0.0001)
				reflection.direction.y must be (1.0 plusOrMinus 0.0001)
				reflection.direction.z must be (0.0 plusOrMinus 0.0001)
				reflection.start must equal(Point3D(1, 0, 0))
			}
		}
	}
}