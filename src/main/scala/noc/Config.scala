package noc

object Config {
  val numDirections = 5
  val numRouters = 16

  def width = math.sqrt(numRouters).toInt

  def x(id: Int) = id % 4

  def y(id: Int) = id / 4
}
