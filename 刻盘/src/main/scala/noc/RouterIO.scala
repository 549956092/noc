package noc

import chisel3._
import chisel3.util._

class RouterIO extends Bundle {
  val in = Vec(Config.numDirections, Flipped(Decoupled(new Packet)))
  val out = Vec(Config.numDirections, Decoupled(new Packet))
}
