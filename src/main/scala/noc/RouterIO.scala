package noc

import chisel3._
import chisel3.util._

class RouterIO extends Bundle {
  val in = Vec(Config.numDirections, Input(new Packet))
  val out = Vec(Config.numDirections, Output(new Packet))
}
