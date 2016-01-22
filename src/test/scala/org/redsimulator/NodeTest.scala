package org.redsimulator


class NodeTest extends UnitSpec {

  "pseudoRandomPosition" should {
    "return always the same sequence of random points given the same input" in {
      val id = 7
      val simulationRand = 12345

      val sequence1 = Seq.tabulate(5)(Node.pseudoRandomPosition(7, simulationRand, _))
      val sequence2 = Seq.tabulate(5)(Node.pseudoRandomPosition(7, simulationRand, _))

      sequence1 should be(sequence2)
    }
  }

  "neighbourClosestTo" should {
    "return the closest neighbour" in new Node {
      val position = Point(0,0)
      val id = 1
      val p = 1.0
      val g = 10

      val n1 = NodeRef(1, null, Point(0.5, 0.54))
      val n2 = NodeRef(2, null, Point(0.5, 0.51))
      val n3 = NodeRef(3, null, Point(0.5, 0.58))
      neighbours = Seq(n1, n2, n3)

      neighbourClosestTo(Point(0.5, 0.5)) should be(Option(n2))
    }
  }

  "neighbourClosestTo" should {
    "return Option.empty when there aren't neighbours" in new Node {
      val position = Point(0,0)
      val id = 1
      val p = 1.0
      val g = 10

      neighbours = Seq()

      neighbourClosestTo(Point(0.5, 0.5)) should be(Option.empty)
    }
  }
}
