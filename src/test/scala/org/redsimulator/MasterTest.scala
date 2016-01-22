package org.redsimulator

import akka.actor.{ActorSystem, ActorRef}
import akka.testkit._
import org.mockito.Mockito._
import org.redsimulator.NodeActor.NetworkView
import scala.collection.mutable

class MasterTest extends TestKit(ActorSystem("testSystem")) with UnitSpec {

  trait Fixture {
    val masterActor = mock[ActorRef]
    val p1 = Point(0.0, 0.0)
    val p2 = Point(0.0, 0.5)
    val p3 = Point(0.0, 0.75)
    val p4 = Point(0.0, 1.01)
    val node1 = NodeRef(1, spy(TestActorRef(NodeActor.props(masterActor, 1, p1))), p1)
    val node2 = NodeRef(2, spy(TestActorRef(NodeActor.props(masterActor, 2, p2))), p2)
    val node3 = NodeRef(3, spy(TestActorRef(NodeActor.props(masterActor, 3, p3))), p3)
    val node4 = NodeRef(4, spy(TestActorRef(NodeActor.props(masterActor, 4, p4))), p4)

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
