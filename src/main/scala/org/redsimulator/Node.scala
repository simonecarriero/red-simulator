package org.redsimulator

import akka.actor.ActorRef

import scala.util.Random

object Node {

  def pseudoRandomPosition(id: Int, rand: Int, counter: Int): Point = {
    val seed = (id + "0" + rand).toLong
    val generator = new Random(seed)

    skipDoubles(2*counter, generator)
    Point(generator.nextDouble, generator.nextDouble)
  }

  private def skipDoubles(n: Int, generator: Random) = {
    for (i <- 1 to n) generator.nextDouble
  }
}

trait Node {
  val id: Int
  val p: Double
  val g: Int
  val position: Point
  var neighbours: Seq[NodeRef] = Seq()

  def neighbourClosestTo(point: Point): Option[NodeRef] = {
    if (neighbours.isEmpty)
      Option.empty
    else
      Option(neighbours.minBy { _.position.distance(point) })
  }
}

case class NodeRef(id: Int, actorRef: ActorRef, position: Point) {
}