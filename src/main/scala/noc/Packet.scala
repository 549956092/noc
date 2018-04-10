package noc

import chisel3._
import chisel3.util._

class Packet extends Bundle {
  val id = UInt(8.W)
  val src = UInt(8.W)
  val dest = UInt(8.W)
  val size = UInt(8.W)
  val dir = UInt(log2Ceil(Config.numDirections).W)
}
