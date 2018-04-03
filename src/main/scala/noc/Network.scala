package noc

import chisel3._
import chisel3.util._

class Network extends Module {
  val io = IO(new NetworkIO)

  io.out.foreach { out =>
    out.id := 0.U
    out.src := 0.U
    out.dest := 0.U
    out.size := 0.U
  }

  val routers = VecInit((0 until Config.numRouters).map(i => Module(new Router).io))

  routers.foreach { router =>
    router.in.foreach { in =>
      in.id := 0.U
      in.src := 0.U
      in.dest := 0.U
      in.size := 0.U
    }
  }

  def connect(from:Int, to:Int, dir: Int) = {
    routers(from).in(dir):= routers(to).out(Direction.opposite(dir))
  }

  (0 until Config.numRouters  ).foreach{i =>

  }
}

object Network extends App {
  Driver.execute(Array("-td", "source/"), () => new Network)
}
