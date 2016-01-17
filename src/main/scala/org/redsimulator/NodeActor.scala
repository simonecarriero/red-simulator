package org.redsimulator

import akka.actor._
import org.redsimulator.NodeActor.{LocationClaimMessage, NetworkView, Start}
import org.redsimulator.MasterActor.Subscription

object NodeActor {
  def props(master: ActorRef, id: Int): Props = Props(classOf[NodeActor], master, id)

  case object Start
  case class NetworkView(n: Seq[NodeRef])
  case class LocationClaimMessage(id: Int, position: Point)
}

class NodeActor(master: ActorRef, val id: Int) extends Actor with Node {
  def receive = {
    case Start => master ! Subscription(position)
    case NetworkView(v) => {
      println(s"received ${v.size} neighbours")
      neighbours = v
      sendLocationClaim
    }
    case LocationClaimMessage(id, position) => {
      println(s"received claim from ${id}")
    }
    case m => println(s"Node received '$m'")
  }

  def sendLocationClaim = {
    neighbours foreach { _.actorRef ! LocationClaimMessage(id, position) }
  }
}
