package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class OddEvenRoutingExampleIO extends Bundle {
  val src = Input(SInt(log2Ceil(Config.numRouters).W))
  val dest = Input(SInt(log2Ceil(Config.numRouters).W))
  val dir = Output(UInt(log2Ceil(Direction.size).W))
}

class OddEvenRoutingExample(val id: Int) extends Module{
  val io = IO(new OddEvenRoutingExampleIO)

  OddEvenRouting(id, io.src, io.dest, io.dir)
}

class OddEvenRoutingExampleTester(example: OddEvenRoutingExample, src: Int, dest: Int) extends PeekPokeTester(example) {
  poke(example.io.dest, dest)

  val dir = peek(example.io.dir)

  scala.Predef.printf(s"out: current=${example.id}, src=$src, dest=$dest, dir=$dir\n")
}

object OddEvenRoutingExampleTester extends App {
  def test(current: Int, src: Int, dest: Int) = {
    chisel3.iotesters.Driver(() => new OddEvenRoutingExample(current)) { example =>
      new OddEvenRoutingExampleTester(example, src, dest)
    }
  }

  OddEvenRoutingVerifyData().foreach(data => test(data.current, data.src, data.dest))

}
