package org.redsimulator

import akka.actor.Actor

class MasterActor extends Actor {
  def receive = {
    case m => {
      println(s"Master received '$m'")
      sender ! "pong"
    }
  }
}
