package noc

object OddEvenRoutingVerify extends App {
  def apply(current: Int, src: Int, dest: Int) = {
    val x = Config.x(current)
    val y = Config.y(current)

    val srcX = Config.x(src)

    val destX = Config.x(dest)
    val destY = Config.y(dest)

    var dir = Direction.local

    if (destX == x) {
      if (destY < y) {
        dir = Direction.north
      } else if (destY >= y) {
        dir = Direction.south
      }
    } else if (destX > x) {
      if (destY == y) {
        dir = Direction.east
      } else if (destY != y) {
        if (((destX - x) % 2 == 1) || x == srcX) {
          if (y > destY) {
            dir = Direction.north
          } else if (y == destY || y < destY) {
            dir = Direction.south
          } else if (((destX - x) % 2 != 1) && x != srcX) {
            if (destX > x) {
              if (destY == y) {
                dir = Direction.east
              } else if (destY != y) {
                if (((destX - x) % 2 == 1) || x == srcX) {
                  if (y > destY) {
                    dir = Direction.north
                  } else if (y < destY & y == destY) {
                    dir = Direction.south
                  }
                  if (destX % 2 == 1 || (destX - x != 1)) {
                    dir = Direction.east
                  }
                }
              }
            }
          }
        } else if (destX % 2 != 1 && (destX - x == 1)) {
          dir = Direction.west
        }
        if ((destX - x) % 2 == 0) {
          if (destY < y) {
            dir = Direction.north
          } else if (destY > y) {
            dir = Direction.north
          }
        }
      }
    }

    dir

    scala.Predef.printf(s"out: current=$current, src=$src, dest=$dest, dir=$dir\n")
  }

  OddEvenRoutingVerifyData().foreach(data => OddEvenRoutingVerify(data.current, data.src, data.dest))
}

case class OddEvenRoutingVerifyData(current: Int, src: Int, dest: Int)

object OddEvenRoutingVerifyData {
  def apply():Seq[OddEvenRoutingVerifyData] = {
    Seq(
//      OddEvenRoutingVerifyData(current = 7, src = 6, dest = 15),
//      OddEvenRoutingVerifyData(current = 8, src = 0, dest = 3),
//      OddEvenRoutingVerifyData(current = 1, src = 0, dest = 2),
      OddEvenRoutingVerifyData(current = 1, src = 2, dest = 0)
    )
  }
}

