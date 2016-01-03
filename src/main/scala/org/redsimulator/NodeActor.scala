package org.redsimulator

import akka.actor._
import org.redsimulator.NodeActor.{NetworkView, Start}
import org.redsimulator.MasterActor.Subscription

object NodeActor {
  def props(master: ActorRef, id: Int): Props = Props(classOf[NodeActor], master, id)

  case object Start
  case class NetworkView(n: Seq[NodeRef])
}

class NodeActor(master: ActorRef, val id: Int) extends Actor with Node {
  def receive = {
    case Start => master ! Subscription(position)
    case NetworkView(v) => {
      println(s"received ${v.size} neighbours")
      neighbours = v
    }
    case m => println(s"Node received '$m'")
  }
}
