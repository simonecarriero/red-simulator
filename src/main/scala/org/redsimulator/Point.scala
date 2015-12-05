package org.redsimulator

import scala.math._

case class Point(x: Double, y: Double) {
  def distance(other: Point): Double =
    sqrt(pow(x - other.x, 2) + pow(y - other.y, 2))
}