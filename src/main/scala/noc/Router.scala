package noc

import chisel3._
import chisel3.util._

class Router extends Module {
  val io = IO(new RouterIO)

  io.out.foreach { out =>
    out.id := 0.U
    out.src := 0.U
    out.dest := 0.U
    out.size := 0.U
  }
}

object Router extends App {
  Driver.execute(Array("-td", "source/"), () => new Router)
}
