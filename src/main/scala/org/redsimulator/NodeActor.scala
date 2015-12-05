package org.redsimulator

import akka.actor._
import NodeActor.Start
import org.redsimulator.MasterActor.Subscription

object NodeActor {
  def props(master: ActorRef): Props = Props(classOf[NodeActor], master)

  case object Start
}

class NodeActor(master: ActorRef) extends Actor with Node {
  def receive = {
    case Start => master ! Subscription(position)
    case m => println(s"Node received '$m'")
  }
}
