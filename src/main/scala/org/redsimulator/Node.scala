package org.redsimulator

import akka.actor.ActorRef

import scala.util.Random

object Node {
  val rand = new Random
}

trait Node {
  import Node._
  val position = Point(rand.nextDouble, rand.nextDouble)
  var neighbours: Seq[NodeRef] = Seq()
}

case class NodeRef(actorRef: ActorRef, position: Point) {
}