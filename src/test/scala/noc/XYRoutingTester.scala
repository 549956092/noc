package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class XYRoutingTester(xYRouting: XYRouting) extends PeekPokeTester(xYRouting) {
  poke(xYRouting.io.in.valid, true)
  poke(xYRouting.io.in.bits.id, 0)
  poke(xYRouting.io.in.bits.src, 0)
  poke(xYRouting.io.in.bits.dest, 0)
  poke(xYRouting.io.in.bits.size, 0)
}

object XYRoutingTester extends App {
  chisel3.iotesters.Driver(()=> new XYRouting(0)) { xYRouting =>
    new XYRoutingTester(xYRouting)
  }
}