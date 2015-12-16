package org.redsimulator

import akka.actor.{ActorSystem, ActorRef}
import akka.testkit._
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.redsimulator.NodeActor.NetworkView
import scala.collection.mutable

class MasterTest extends TestKit(ActorSystem("testSystem")) with UnitSpec {

  trait Fixture {
    val masterActor = mock[ActorRef]
    val node1 = NodeRef(spy(TestActorRef(NodeActor.props(masterActor))), Point(0.0, 0.0))
    val node2 = NodeRef(spy(TestActorRef(NodeActor.props(masterActor))), Point(0.0, 0.5))
    val node3 = NodeRef(spy(TestActorRef(NodeActor.props(masterActor))), Point(0.0, 0.75))
    val node4 = NodeRef(spy(TestActorRef(NodeActor.props(masterActor))), Point(0.0, 1.01))

    val master = new Master {
      override val r = 0.5
      nodes = mutable.Seq(node1, node2, node3, node4)
    }

  }

  "setupNetwork" should {
    "send neighbours to nodes" in new Fixture {
      master.setupNetwork()

      verify(node1.actorRef) ! NetworkView(Seq(node2))
      verify(node2.actorRef) ! NetworkView(Seq(node1, node3))
      verify(node3.actorRef) ! NetworkView(Seq(node2, node4))
      verify(node4.actorRef) ! NetworkView(Seq(node3))
    }
  }
}
