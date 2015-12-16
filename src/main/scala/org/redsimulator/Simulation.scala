package org.redsimulator

import akka.actor.{Props, ActorSystem}
import org.redsimulator.NodeActor.Start

object Simulation extends App {
  implicit val system = ActorSystem("RedSimulatorSystem")
  val master = system.actorOf(Props(classOf[MasterActor]), "masterActor")
  val numberOfNodes = 10
  def createActor(i: Int) = system.actorOf(NodeActor.props(master), s"NodeActor$i")

  List.range(1, numberOfNodes)
    .map {createActor}
    .foreach { _ ! Start }
}
