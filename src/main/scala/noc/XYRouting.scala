package noc


import chisel3._
import chisel3.util._

class XYRoutingIO extends Bundle {
  val in = Flipped(Decoupled(new Packet))
  val out = Decoupled(new Packet)
}

class XYRouting(val id:Int) extends Module {
  val io = IO(new XYRoutingIO)

  io.in.ready := true.B

  io.out.valid := false.B
  io.out.bits := DontCare

  when(io.in.fire()) {
    io.out.valid := true.B
    io.out.bits := io.in.bits

    val x = Config.x(id.U)
    val y = Config.y(id.U)

    val destX = Config.x(io.in.bits.dest)
    val destY = Config.y(io.in.bits.dest)

    when (x > destX){
      io.out.bits.dir := Direction.west.U
    }.elsewhen(x < destX){
      io.out.bits.dir := Direction.east.U
    }.elsewhen(y > destY){
      io.out.bits.dir := Direction.north.U
    }.elsewhen(y < destY){
      io.out.bits.dir := Direction.south.U
    }.otherwise{
      io.out.bits.dir := Direction.local.U
    }
  }

}
