package org.redsimulator

import scala.math.sqrt

class PointTest extends UnitSpec {

  "equals" should {
    "return true if the given point has the same coordinates" in {
      val a = Point(0.1, 0.2)
      val b = Point(0.1, 0.2)
      a should be (b)
    }
    "return false if the given point has different coordinates" in {
      val a = Point(0.1, 0.2)
      val b = Point(0.1, 0.21)
      a should not be (b)
    }
  }

  "distance" should {
    "calculate the distance from the given point " in  {
      val a = Point(0.0, 0.0)
      val b = Point(1.0, 1.0)
      a.distance(b) should be (sqrt(2))
    }
  }
}