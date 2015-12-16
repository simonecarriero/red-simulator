package org.redsimulator

import org.scalatest._
import org.scalatest.mock.MockitoSugar

trait UnitSpec
  extends WordSpecLike
  with Matchers
  with OptionValues
  with Inspectors
  with MockitoSugar {
}
