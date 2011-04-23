package raytracer.geom
import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Vector3DTest extends Spec with MustMatchers {

	describe("A vector") {
		describe("when 0,0,0") {
			val v = Vector3D(0, 0, 0)
			
			it("must not change a point when added to that point") {
				val p = Point3D(1, 2, 3)
				(p + v) must equal(p)
			}
			
			it("must have a magnitude of 0") {
				(v magnitude) must equal(0)
			}
		}
		describe("when 1,1,1") {
			val v = Vector3D(1, 1, 1)
			it ("must have a magnitude of sqrt 3") {
				(v magnitude) must equal(Math.sqrt(3.0))
			}
			describe("and normalised") {
				val n = v.normalised
				it ("must have a magnitude of 1") {
					(n magnitude) must equal(1)
				}
				it ("must have all components the same") {
					(n.x) must equal (n.y)
					(n.y) must equal (n.z) 
				}
			}
		}
	}
	
}