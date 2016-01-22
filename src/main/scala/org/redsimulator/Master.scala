package org.redsimulator

import akka.actor.ActorRef
import org.redsimulator.NodeActor.NetworkView

import scala.collection.mutable

trait Master {
  val r: Double
  var nodes: mutable.Seq[NodeRef] = mutable.Seq()

  def addNode(id: Int, nodeActor: ActorRef, position: Point): Unit = {
    nodes = nodes :+ NodeRef(id, nodeActor, position)
    println(s"Master received subscription from $nodeActor in ${position}")
  }

  def setupNetwork(): Unit = {
    for (node <- nodes) {
      val neighbours = nodes
        .filter(n => n.position != node.position)
        .filter(n => n.position.distance(node.position) <= r)

      node.actorRef ! NetworkView(neighbours)
    }
  }
}