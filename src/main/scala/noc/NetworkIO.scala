package noc

import chisel3._
import chisel3.util._

class NetworkIO extends Bundle {
  val in = Vec(Config.numRouters, Input(new Packet))
  val out = Vec(Config.numRouters, Output(new Packet))
}
