package org.redsimulator

import scala.util.Random

object Node {
  val rand = new Random
}

trait Node {
  import Node._
  val position = Point(rand.nextDouble, rand.nextDouble)
}
