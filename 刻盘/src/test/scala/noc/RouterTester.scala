package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class RouterTester(router: Router) extends PeekPokeTester(router){
  router.io.in.foreach{in =>
    poke(in.valid, false)
  }

  while (peek(router.io.in(Direction.local).ready) == BigInt(0)){
    step(1)
  }

  poke(router.io.in(Direction.local).valid, true)

  poke(router.io.in(Direction.local).bits.id, 0)
  poke(router.io.in(Direction.local).bits.src, 5)
  poke(router.io.in(Direction.local).bits.dest, 11)
  poke(router.io.in(Direction.local).bits.size, 16)

  while (peek(router.io.out(Direction.east).valid) == BigInt(0)){
    step(1)
  }

  val  valid = peek(router.io.out(Direction.east).valid)
  val  id = peek(router.io.out(Direction.east).bits.id)
  val  src = peek(router.io.out(Direction.east).bits.src)
  val  dest = peek(router.io.out(Direction.east).bits.dest)
  val  size = peek(router.io.out(Direction.east).bits.size)

  scala.Predef.printf(s"[$t RouterTester] out: valid=$valid, id=$id, src=$src, dest=$dest, size=$size\n")
}

object RouterTester extends App {
  chisel3.iotesters.Driver(()=> new Router(5)) { router =>
    new RouterTester(router)
  }
}
