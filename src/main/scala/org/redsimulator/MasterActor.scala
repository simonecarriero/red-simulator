package org.redsimulator

import akka.actor.FSM
import scala.concurrent.duration._
import org.redsimulator.MasterActor._

object MasterActor {
  sealed trait State
  case object WaitingSubscriptions extends State
  case object SimulationRunning extends State

  sealed trait Data
  case object NoData extends Data
  case class Subscription(position: Point) extends Data
}

class MasterActor extends FSM[State, Data] with Master {
  override val r = 0.1

  startWith(WaitingSubscriptions, NoData)

  when(WaitingSubscriptions, stateTimeout = 5 seconds) {
    case Event(s: Subscription, _) => {
      addNode(sender, s.position)
      stay
    }
    case Event(StateTimeout, _) => {
      setupNetwork()
      goto(SimulationRunning)
    }
  }

  when(SimulationRunning) {
    case Event(e, _) => stay
  }
}
