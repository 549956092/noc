package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class XYRoutingExampleIO extends Bundle {
  val dest = Input(UInt(log2Ceil(Config.numRouters).W))
  val dir = Output(UInt(log2Ceil(Direction.size).W))
}

class XYRoutingExample(val id: Int) extends Module{
  val io = IO(new XYRoutingExampleIO)

  XYRouting(id, io.dest, io.dir)
}

class XYRoutingExampleTester(example: XYRoutingExample, src: Int, dest: Int) extends PeekPokeTester(example) {
  poke(example.io.dest, dest)

  val dir = peek(example.io.dir)
  scala.Predef.printf(s"out: current=${example.id}, dest=$dest, dir=$dir\n")
}

object XYRoutingExampleTester extends App {
  def test(current: Int, src: Int, dest: Int) = {
    chisel3.iotesters.Driver(() => new XYRoutingExample(current)) { example =>
      new XYRoutingExampleTester(example, src, dest)
    }
  }

  test(current = 7, src = 6, dest = 15)
  test(current = 8, src = 0, dest = 3)
}