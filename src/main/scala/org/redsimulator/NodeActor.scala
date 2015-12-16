package org.redsimulator

import akka.actor._
import org.redsimulator.NodeActor.{NetworkView, Start}
import org.redsimulator.MasterActor.Subscription

object NodeActor {
  def props(master: ActorRef): Props = Props(classOf[NodeActor], master)

  case object Start
  case class NetworkView(n: Seq[NodeRef])
}

class NodeActor(master: ActorRef) extends Actor with Node {
  def receive = {
    case Start => master ! Subscription(position) // is start needed? can't the subscription be fired in the constructor?
    case NetworkView(v) => {
      println(s"received ${v.size} neighbours")
      neighbours = v
    }
    case m => println(s"Node received '$m'")
  }
}
