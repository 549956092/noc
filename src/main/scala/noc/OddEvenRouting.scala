package noc

import chisel3._
import chisel3.util._

object OddEvenRouting {
  def apply(current: Int, src: SInt, dest: SInt, dir: UInt) = {
    val x = Config.x(current.S)
    val y = Config.y(current.S)

    val srcX = Config.x(src)

    val destX = Config.x(dest)
    val destY = Config.y(dest)

    dir := Direction.local.U

    when(destX === x) {
      when(destY < y) {
        dir := Direction.north.U
      }.elsewhen(destY >= y) {
        dir := Direction.south.U
      }
    }.elsewhen(destX > x) {
      when(destY === y) {
        dir := Direction.east.U
      }.elsewhen(destY =/= y) {
        when(((destX - x) % 2.S === 1.S) || x === srcX) {
          when(y > destY) {
            dir := Direction.north.U
          }.elsewhen(y === destY || y < destY) {
            dir := Direction.south.U
          }.elsewhen(((destX - x) % 2.S =/= 1.S) && x =/= srcX) {
            when(destX > x) {
              when(destY === y) {
                dir := Direction.east.U
              }.elsewhen(destY =/= y) {
                when(((destX - x) % 2.S === 1.S) || x === srcX) {
                  when(y > destY) {
                    dir := Direction.north.U
                  }.elsewhen(y < destY & y === destY) {
                    dir := Direction.south.U
                  }
                  when(destX % 2.S === 1.S || (destX - x =/= 1.S)) {
                    dir := Direction.east.U
                  }
                }
              }
            }
          }
    }.elsewhen(destX % 2.S =/= 1.S && (destX - x === 1.S)) {
          dir := Direction.west.U
        }
        when((destX - x) % 2.S === 0.S) {
          when(destY < y) {
            dir := Direction.north.U
          }.elsewhen(destY > y) {
            dir := Direction.north.U
          }
        }
      }
    }
  }
}
