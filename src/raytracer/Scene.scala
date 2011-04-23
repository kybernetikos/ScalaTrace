package raytracer

import raytracer.shapes._
import raytracer.geom._
import raytracer.light._
import java.awt.Color
import java.io.PrintStream
import java.io.FileOutputStream
import scala.io.Source.fromFile
import java.awt.image.BufferedImage
import raytracer.util.ColorUtil.fromColor

class Scene {
	var shapes: List[Shape] = List()
	var lights: List[Light] = List()
	var ambient: Double = 0.4
	
	def add(light: Light) = {
		lights = light :: lights
	}
	
	def add(shape: Shape) = {
		shapes = shape :: shapes
	}
	
	def closestIntersectedShape(ray: Ray, excluding: Option[Shape]): Option[(Shape, Double)] = {
		def deOptionSecond[T,U](a : (T, Option[U])): Option[(T, U)] = a match {
			case (_, None) => None
			case (a, Some(b)) => Some(a, b)
		}
		val remainingShapes = excluding match {
			case None => shapes
			case Some(shape) => shapes.filter(_ != shape)
		}
		val results = remainingShapes.zip(remainingShapes.map(_.distanceAlongRay(ray))).map(deOptionSecond).flatten.filter((a)=>a._2>0).sort((a, b)=>a._2<b._2)
		
		if (results.isEmpty) None else Some(results(0))
	}
	
	def mix(base: Color, proportion: Double, mix: Color): Color = base * (1 - proportion) + mix * proportion
	
	def zipWith[A,B,C](a: List[A], f: (A,B)=>C, b: List[B]): List[C] = a.zip(b).map((pair)=>f(pair._1, pair._2))
	def filterBy[A,B](a: List[A], b: List[B], f: (B)=>Boolean) = a.zip(b).filter((pair)=>f(pair._2)).map(_._1) 
	
	def colorOfRayIntersection(ray: Ray, maxReflections: Double, excluding: Option[Shape]): Color = {
		closestIntersectedShape(ray, excluding) match {
			case None => Color.BLACK
			case Some((shape, t)) => {
				val p = ray(t)
				val normal = shape.normalAt(p)

				val lightDists = lights.map(_.distanceFrom(p))
				val lightDirs = lights.map(_.directionFrom(p))
				val raysToLights = lightDirs.map(new Ray(p, _))
				val notShadowed = raysToLights.map(closestIntersectedShape(_, Some(shape))).zip(lightDists).map(
					_ match {
						case (None, _) => true
						case (Some((_, shapeDist)), lightDist) => shapeDist > lightDist
					}
				)
				
				val notShadowedLights = filterBy(lights, notShadowed, (a: Boolean)=>a)
				val notShadowedDirs = filterBy(lightDirs, notShadowed, (a: Boolean)=>a)
				val lightIntensity = notShadowedLights.map(_.intensity)
				val lightSpreads = notShadowedDirs.map(normal ⋅ _)
				val hs = notShadowedDirs.map(_ - ray.direction).map(_.normalised) 
				val specular = zipWith(hs.map((h)=>Math.pow(h ⋅ normal, shape.surface.shininess)), (a:Double,b:Double)=>shape.surface.specular*a*b, lightIntensity)
				val speculars = filterBy(specular, lightSpreads, (_:Double)>0)
				val diffuses = zipWith(lightSpreads, (a:Double,b:Double)=>a*b, lightIntensity).filter(_>0).map(shape.surface.diffuse * _)

				val ambientComponent = shape.surface.diffuse * ambient
				val specularComponent = if (speculars.isEmpty) Color.BLACK else speculars.reduceLeft(_+_)
				val diffuseComponent: Color = if (diffuses.isEmpty) Color.BLACK else diffuses.reduceLeft(_+_)
				
				val baseColor = ambientComponent + specularComponent + diffuseComponent
				
				if (maxReflections > 0 && shape.surface.specular != Color.BLACK) {
					val reflection = ray.reflect(p, normal)
					val mixingColor = colorOfRayIntersection(reflection, maxReflections - 1, Some(shape))
					baseColor + mixingColor * shape.surface.specular
				} else {
					baseColor
				}
			}
		}
	}
	
	def render(eye: Point3D, width: Int, height: Int): BufferedImage = {
		val image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		val topLeftOfScreen = Point3D(-width / 2.0, -height / 2.0, 0)
		
		for (x <- 0 until width) {
			for (y <- 0 until height) {
				val pixel = Point3D(x + topLeftOfScreen.x, y + topLeftOfScreen.y, 0)
				val ray = new Ray(eye, eye → pixel)
				val color = colorOfRayIntersection(ray, 1, None)
				image.setRGB(x, y, color.getRGB());
			}
		}
		image
	}
	
	def write(filename: String): Unit = {
		val out: PrintStream = new PrintStream(new FileOutputStream(filename))
		if ( ! lights.isEmpty) {
			out.println("# lights")
			for (light <- lights) {
				out.print(light.getClass.getName)
				out.print(", ")
				out.println(light.toCSV)
			}
			out.println()
		}
		out.println("# shapes")
		for (shape <- shapes) {
			out.print(shape.getClass.getName)
			out.print(",\t")
			out.println(shape.toCSV)
		}
	}
	
	def read(filename: String): Unit = {
		for (line <- fromFile(filename).getLines) {
			if ( line.trim().length() > 0 && ! line.startsWith("#")) {
				val elements : Array[String] = line.split(",").map(_.trim())
				elements.head match {
					case "raytracer.shapes.Sphere" =>
						add(Sphere.fromElements(elements.tail))
					case "raytracer.shapes.Plane" =>
						add(Plane.fromElements(elements.tail))
					case "raytracer.shapes.AACuboid" =>
						add(AACuboid.fromElements(elements.tail))
					case "raytracer.light.PositionalLight" =>
						add(PositionalLight.fromElements(elements.tail))
				}
			}
		}
	}
}