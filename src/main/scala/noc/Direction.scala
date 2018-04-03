package noc

object Direction {
  val local = 0
  val north = 1
  val east = 2
  val south = 3
  val west = 4

  def opposite(direction: Int) = {
    direction match {
      case `north` => south
      case `east` => west
      case `south` => north
      case `west` => east
      case _ => local
    }
  }
}
