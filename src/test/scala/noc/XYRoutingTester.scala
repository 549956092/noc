package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class XYRoutingTester(routing: XYRouting, src: Int, dest: Int) extends PeekPokeTester(routing) {
  poke(routing.io.in.valid, true)

  poke(routing.io.in.bits.id, 0)
  poke(routing.io.in.bits.src, src)
  poke(routing.io.in.bits.dest, dest)
  poke(routing.io.in.bits.size, 16)

  poke(routing.io.out.ready, true)

  val dir = peek(routing.io.out.bits.dir)
  scala.Predef.printf(s"out: current=${routing.id}, dest=$dest, dir=$dir\n")
}

object XYRoutingTester extends App {
  def test(current: Int, src: Int, dest: Int) = {
    chisel3.iotesters.Driver(() => new XYRouting(current)) { routing =>
      new XYRoutingTester(routing, src, dest)
    }
  }

  test(current = 7, src = 6, dest = 15)
  test(current = 8, src = 0, dest = 3)
}