package org.redsimulator

import akka.actor._
import org.redsimulator.Node.pseudoRandomPosition
import org.redsimulator.NodeActor.{ControlMessage, LocationClaimMessage, NetworkView, Start}
import org.redsimulator.MasterActor.Subscription

import scala.collection.mutable
import scala.util.Random

object NodeActor {
  val rand = new Random
  def randomPoint = Point(NodeActor.rand.nextDouble, NodeActor.rand.nextDouble)
  def props(master: ActorRef, id: Int, position: Point = randomPoint): Props = Props(classOf[NodeActor], master, id, position)

  case object Start
  case class NetworkView(neighbours: Seq[NodeRef])
  case class LocationClaimMessage(id: Int, position: Point)
  case class ControlMessage(locationClaim: LocationClaimMessage, destination: Point)
}

class NodeActor(master: ActorRef, val id: Int, val pos: Point) extends Actor with Node {
  val simulationRand = 12345 //todo to be sent from master
  override val position = pos
  override val p = 1.0
  override val g = 1

  var storedLocationClaims: mutable.Seq[LocationClaimMessage] = mutable.Seq()

  def receive = {
    case Start => processStart
    case m: NetworkView => processNetworkView(m) 
    case m: LocationClaimMessage => processLocationClaimMessage(m)
    case m: ControlMessage => processControlMessage(m)
  }

  def processStart = {
    val subscription = Subscription(id, position)
    master ! subscription
  }

  def processNetworkView(msg: NetworkView) = {
    neighbours = msg.neighbours
    sendLocationClaim
  }

  def sendLocationClaim = {
    neighbours foreach { _.actorRef ! LocationClaimMessage(id, position) }
  }

  def processLocationClaimMessage(msg: LocationClaimMessage) = {
    withProbabilityP {
      for (i <- 1 to g) {
        val destination = pseudoRandomPosition(id, simulationRand, i)
        val controlMessage = ControlMessage(msg, destination)
        processControlMessage(controlMessage)
      }
    }
  }

  def processControlMessage(msg: ControlMessage) = {
    val closestNeighbour = neighbourClosestTo(msg.destination)
    if (isCloser(closestNeighbour, msg.destination)) {
      println(closestNeighbour.get.id + " is closer than " + id)
      storedLocationClaims = storedLocationClaims :+ msg.locationClaim
      closestNeighbour.get.actorRef ! msg
    }
    else {
      checkForClone(msg.locationClaim.id, msg.locationClaim.position)
    }
  }

  private def isCloser(closestNeighbour: Option[NodeRef], destination: Point) =
    closestNeighbour.isDefined && closestNeighbour.get.position.distance(destination) < position.distance(destination)

  def checkForClone(claimId: Int, claimPosition: Point): Unit = {
    val clone = storedLocationClaims.find(c => c.id == claimId && c.position != claimPosition)
    if (clone.isDefined) {
      println(s"Found clone. Node ${claimId} claimed to be in both ${claimPosition} and ${clone.get.position}")
    }
  }

  def withProbabilityP(f: => Unit) = if (NodeActor.rand.nextDouble() < p) f
}
