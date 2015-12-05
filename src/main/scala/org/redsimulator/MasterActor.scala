package org.redsimulator

import akka.actor.Actor
import org.redsimulator.MasterActor.Subscription

object MasterActor {
  case class Subscription(position: Point)
}

class MasterActor extends Actor {
  def receive = {
    case s: Subscription => {
      println(s"Master received subscription from $sender in ${s.position}")
    }
  }
}
