package noc

import chisel3._
import chisel3.util._

object Config {
  val numDirections = 5
  val numRouters = 16

  def width = math.sqrt(numRouters).toInt

  def x(id: Int) = id % 4

  def y(id: Int) = id / 4

  def x(id: UInt) = id % 4.U

  def y(id: UInt) = id / 4.U

  def x(id: SInt) = id % 4.S

  def y(id: SInt) = id / 4.S
}
