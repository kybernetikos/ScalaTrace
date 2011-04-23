package raytracer.geom
import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Point3DTest extends Spec with MustMatchers {
	describe("A 3d Point") {
		val p = Point3D(5, 2, 0)
		
		it("must produce a vector of magnitude 0 when subtacted from itself") {
			((p - p) magnitude) must be === 0
		}
		it("must convert to csv correctly") {
			(p.toCSV) must equal ("5.0,2.0,0.0")
		}
		it("is equal to another point with the same coordinates") {
			p must equal (Point3D(5, 2, 0))
		}
	}
}