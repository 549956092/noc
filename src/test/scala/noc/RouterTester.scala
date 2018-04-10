package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class RouterTester(router: Router) extends PeekPokeTester(router){
  router.io.in.foreach{in =>
    poke(in.id, 0)
    poke(in.src, 0)
    poke(in.dest, 0)
    poke(in.size, 0)
  }

  router.io.out.foreach{out =>
    val  id = peek(out.id)
    val  src = peek(out.src)
    val  dest = peek(out.dest)
    val  size = peek(out.size)

    scala.Predef.printf(s"id=$id, src=$src, dest=$dest, size=$size\n")
  }
}

object RouterTester extends App {
  chisel3.iotesters.Driver(()=> new Router()) { router =>
    new RouterTester(router)
  }
}
