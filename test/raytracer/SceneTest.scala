package raytracer

import org.scalatest.Spec
import org.scalatest.matchers.MustMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import raytracer.geom._
import raytracer.shapes._
import java.awt.Color

@RunWith(classOf[JUnitRunner])
class SceneTest extends Spec with MustMatchers {
	describe("A Scene") {
		val scene = new Scene()
		describe("with two Spheres") {
			val sphere1 = new Sphere(Point3D(2, 0, 0), 1, new SurfaceProperty(0, Color.RED))
			val sphere2 = new Sphere(Point3D(5, 0, 0), 1, new SurfaceProperty(0, Color.RED)) 
			scene.add(sphere1)
			scene.add(sphere2)
			
			it("must return the closest") {
				val ray = Ray(Point3D(0, 0, 0), Vector3D(1, 0, 0))
				scene.closestIntersectedShape(ray, None) match {
					case Some((p, _)) => p must equal(sphere1)
					case None => fail
				}
			}
		}
	}
	
}