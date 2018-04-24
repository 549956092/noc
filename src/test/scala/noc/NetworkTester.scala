package noc

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._

class NetworkTester(network: Network) extends PeekPokeTester (network){

}
object NetworkTester extends  App{
  chisel3.iotesters.Driver(() => new Network()){
    network => new NetworkTester(network)
  }
}