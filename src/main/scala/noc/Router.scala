package noc

import chisel3._
import chisel3.util._

class Router extends Module {
  val io = IO(new RouterIO)

  io.out.foreach { out =>
    out.valid := false.B
    out.bits := DontCare
  }

  io.in.foreach { in =>
    in.ready := false.B
  }
}

object Router extends App {
  Driver.execute(Array("-td", "source/"), () => new Router)
}
