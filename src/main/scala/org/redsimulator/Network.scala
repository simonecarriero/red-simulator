package org.redsimulator

import akka.actor.{Props, ActorSystem}
import NodeActor.Start
import org.redsimulator.NodeActor.Start

object Network extends App {
  implicit val system = ActorSystem("RedSimulatorSystem")
  val master = system.actorOf(Props(classOf[MasterActor]), "masterActor")
  val numberOfNodes = 10;
  def createActor(i: Integer) = system.actorOf(NodeActor.props(master), s"NodeActor$i")

  List.range(1, numberOfNodes)
    .map { createActor(_) }
    .foreach { _ ! Start }
}
