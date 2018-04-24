package noc


import chisel3._
import chisel3.util._

object XYRouting {
  def apply(current: Int, dest: UInt, dir: UInt) = {
    val x = Config.x(current.U)
    val y = Config.y(current.U)

    val destX = Config.x(dest)
    val destY = Config.y(dest)

    when(x > destX) {
      dir := Direction.west.U
    }.elsewhen(x < destX) {
      dir := Direction.east.U
    }.elsewhen(y > destY) {
      dir := Direction.north.U
    }.elsewhen(y < destY) {
      dir := Direction.south.U
    }.otherwise {
      dir := Direction.local.U
    }
  }
}
