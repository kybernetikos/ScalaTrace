package raytracer.util

import java.awt.Color

class ColorUtil(c: Color) {
	def toCSV = c.getRed+","+c.getGreen+","+c.getBlue
	def *(num: Double) = new Color(
			Math.max(0, Math.min(255, Math.round(Math.round(c.getRed * num)))), 
			Math.max(0, Math.min(255, Math.round(Math.round(c.getGreen * num)))), 
			Math.max(0, Math.min(255, Math.round(Math.round(c.getBlue * num)))))
	def *(other: Color) = new Color(
			Math.max(0, Math.min(255, Math.round(Math.round(c.getRed * (other.getRed / 255.0))))), 
			Math.max(0, Math.min(255, Math.round(Math.round(c.getGreen * (other.getGreen / 255.0))))), 
			Math.max(0, Math.min(255, Math.round(Math.round(c.getBlue * (other.getBlue / 255.0))))))
	def +(other: Color) = new Color(
			Math.max(0, Math.min(255, c.getRed + other.getRed)),
			Math.max(0, Math.min(255, c.getGreen + other.getGreen)),
			Math.max(0, Math.min(255, c.getBlue + other.getBlue)))
}

object ColorUtil {
	implicit def fromColor(c: Color): ColorUtil = new ColorUtil(c)
	def fromElements(elements: Array[String]): Color = new Color(elements(0).toInt, elements(1).toInt, elements(2).toInt)
}