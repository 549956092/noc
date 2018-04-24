package noc

import chisel3._
import chisel3.util._

class Router(id: Int) extends Module {
  val io = IO(new RouterIO)

  io.out.foreach { out =>
    out.valid := false.B
    out.bits := DontCare
  }

  io.in.foreach { in =>
    in.ready := false.B
  }

  val inputBuffers = VecInit((0 until Direction.size).map(i => Module(new Queue(new Packet(), 16)).io))

  val dirOut = Wire(Vec(Direction.size, UInt(log2Ceil(Direction.size).W)))

  val demuxes = VecInit((0 until Direction.size).map(i => Module(new Demux(new Packet(), Direction.size)).io))

  val arbiters = VecInit((0 until Direction.size).map(i => Module(new RRArbiter(new Packet(), Direction.size)).io))

  io.in <> inputBuffers.map(_.enq)

  inputBuffers.zip(dirOut).foreach { case (inputBuffer, dir) => XYRouting(id, inputBuffer.deq.bits.dest, dir) }

  inputBuffers.zipWithIndex.foreach { case (inputBuffer, i) =>
    val demux = demuxes(i)
    val dir = dirOut(i)

    demux.in.valid := inputBuffer.deq.valid
    demux.in.bits := inputBuffer.deq.bits
   inputBuffer.deq.ready := demux.in.ready

    demux.select := dir
  }

  io.out.zipWithIndex.foreach { case (out, i) =>
    val arbiter = arbiters(i)

    arbiter.in.zip(demuxes.map(_.out(i))).foreach{case(a,b) =>
      a.valid := b.valid
      a.bits := b.bits
      b.ready := a.ready
    }

    out <> arbiter.out
  }
}

object Router extends App {
  Driver.execute(Array("-td", "source/"), () => new Router(0))
}
