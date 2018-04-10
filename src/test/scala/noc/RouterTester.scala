package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class RouterTester(router: Router) extends PeekPokeTester(router){
  router.io.in.foreach{in =>
    poke(in.valid, true)

    poke(in.bits.id, 0)
    poke(in.bits.src, 0)
    poke(in.bits.dest, 0)
    poke(in.bits.size, 0)
  }

  router.io.out.foreach{out =>
    val  valid = peek(out.valid)
    val  id = peek(out.bits.id)
    val  src = peek(out.bits.src)
    val  dest = peek(out.bits.dest)
    val  size = peek(out.bits.size)

    scala.Predef.printf(s"out: valid=$valid, id=$id, src=$src, dest=$dest, size=$size\n")
  }
}

object RouterTester extends App {
  chisel3.iotesters.Driver(()=> new Router()) { router =>
    new RouterTester(router)
  }
}
