package noc

import chisel3._
import chisel3.util._

class Packet extends Bundle {
  val id = UInt(8.W)
  val src = UInt(log2Ceil(Config.numRouters).W)
  val dest = UInt(log2Ceil(Config.numRouters).W)
  val size = UInt(8.W)
}
