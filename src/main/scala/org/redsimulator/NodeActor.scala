package org.redsimulator

import akka.actor._
import NodeActor.Start

object NodeActor {
  def props(master: ActorRef): Props = Props(classOf[NodeActor], master)

  case object Start
}

class NodeActor(master: ActorRef) extends Actor {
  def receive = {
    case Start => master ! "ping"
    case m => println(s"Node received '$m'")
  }
}
