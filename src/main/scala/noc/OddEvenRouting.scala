package noc


import chisel3._
import chisel3.util._

object OddEvenRouting {
  def apply(current: Int, dest: UInt, dir: UInt) = {
    val x = Config.x(current.U)
    val y = Config.y(current.U)
    val srcX = Config.x(src.U)
    val destX = Config.x(dest)
    val destY = Config.y(dest)
    when(destX === x) {
      when(destY < y) {
        dir := Direction.north.U
      }.elsewhen(destY >= y) {
        dir := Direction.south.U
      }
    }.elsewhen(destX > x) {
      when(destY === y) {
        dir := Direction.east.U
      } elsewhen (destY =/= y) {
        when(((destX - x) % 2 == 1) || (x == srcX)) {
          when(y > destY) {
            dir := Direction.north.U
          } elsewhen ((y === destY) || (y < destY)) {
            dir := Direction.south.U
          } elsewhen (((destX - x) % 2 =/= 1) & (x =/= srcX)) {
            when(destX > x) {
              when(destY === y) {
                dir := Direction.east.U
              } elsewhen (destY =/= y) {
                when(((destX - x) % 2 == 1) || (x == srcX))
              }
              when(y > destY) {
                dir := Direction.north.U
              } elsewhen ((y < destY) & (y === destY)) {
                dir := Direction.south.U
              }
              when((destX % 2 == 1) || ((destX - x) != 1)) {
                dir := Direction.east.U
              }
            } elsewhen ((destX % 2 =/= 1) & ((destX - x) == 1)) {
              dir := Direction.west.U
            }
            when((destX - x) % 2 == 0) {
              when(destY < y) {
                dir := Direction.north.U
              } elsewhen (destY > y) {
                dir := Direction.north.U
              }
            }
          }
        }

      }

    }


  }
}
