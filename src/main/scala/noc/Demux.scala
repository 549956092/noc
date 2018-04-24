package noc

import chisel3._
import chisel3.util._

class DemuxIO[T <: Data](gen: T, n: Int) extends Bundle {
  val select = Input(UInt(log2Ceil(n).W))
  val in = Flipped(Decoupled(gen))
  val out = Vec(n, Decoupled(gen))

  override def cloneType = new DemuxIO(gen, n).asInstanceOf[this.type]
}

class Demux[T <: Data](gen: T, n: Int) extends Module {
  val io = IO(new DemuxIO(gen, n))

  io.in.ready := false.B

  io.out.zipWithIndex.foreach { case (out, i) =>
    out.bits := io.in.bits
    when(i.U =/= io.select) {
      out.valid := false.B
    }.otherwise {
      out.valid := io.in.valid
      io.in.ready := out.ready
    }
  }
}