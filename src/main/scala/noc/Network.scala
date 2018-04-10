package noc

import chisel3._
import chisel3.util._

class Network extends Module {
  val io = IO(new NetworkIO)

  io.out.foreach { out =>
    out.valid:=false.B
    out.bits:=DontCare
  }

  val routers = VecInit((0 until Config.numRouters).map(i => Module(new Router).io))

  routers.foreach { router =>
    router.in.foreach { in =>
      in.valid:=false.B
      in.bits:=DontCare
    }
  }

  def connect(from: Int, to: Int, dir: Int) = {
    routers(from).in(dir) := routers(to).out(Direction.opposite(dir))

    scala.Predef.printf(s"connect: from=$from, to=$to, dir=$dir\n")
  }

  (0 until Config.numRouters).foreach { i =>
    val x = Config.x(i)
    val y = Config.y(i)

    if (y > 0) {
      connect(i, i - Config.width, Direction.north)
    }
    if (x < (Config.width - 1)) {
      connect(i, i + 1, Direction.east)
    }
    if (y < (Config.width - 1)) {
      connect(i, i + Config.width, Direction.south)
    }
    if (x > 0) {
      connect(i, i - 1, Direction.west)
    }
  }
}

object Network extends App {
  Driver.execute(Array("-td", "source/"), () => new Network)
}
