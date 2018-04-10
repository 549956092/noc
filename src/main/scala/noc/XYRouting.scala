package noc


import chisel3._
import chisel3.util._

class XYRoutingIO extends Bundle {
  val in = Flipped(Decoupled(new Packet))
  val out = Decoupled(new Packet)
}

class XYRouting(id:Int) extends Module {
  val io = IO(new XYRoutingIO)

  io.in.ready := true.B

  io.out.valid := false.B
  io.out.bits := DontCare

  when(io.in.fire()) {
    io.out.valid := true.B
    io.out.bits := io.in.bits




  }

}
