package org.redsimulator

import akka.actor.{Props, ActorSystem}
import org.redsimulator.NodeActor.Start

object Simulation extends App {
  implicit val system = ActorSystem("RedSimulatorSystem")
  val master = system.actorOf(Props(classOf[MasterActor]), "masterActor")
  val numberOfNodes = 10
  def createActor(id: Int) = system.actorOf(NodeActor.props(master, id), s"NodeActor$id")
  def createCloneActor = system.actorOf(NodeActor.props(master, 1), s"NodeActor1Clone")

  List.range(1, numberOfNodes) map createActor foreach { _ ! Start }
  createCloneActor ! Start
}
