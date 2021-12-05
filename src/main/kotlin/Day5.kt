import kotlin.streams.toList

object Day5 {
    val part2 = true
    val print = false
    val diagonal = part2
    val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()

    data class Point(val x: Int, val y: Int)
    data class Line(val from: Point, val to: Point) {
        fun hit(p: Point): Boolean {
            val ys = listOf(from.y, to.y).sorted()
            val xs = listOf(from.x, to.x).sorted()
            val rise = to.y - from.y
            val run = to.x - from.x
            if (rise == 0 || run == 0) {
                return when {
                    p.y == from.y && p.y == to.y && p.x in (xs[0]..xs[1]) -> true
                    p.x == from.x && p.x == to.x && p.y in (ys[0]..ys[1]) -> true
                    else -> false
                }
            }
            if (!diagonal) {
                return false
            }
            val m = rise / run
            val b = from.y - m * from.x
            return m * p.x + b - p.y == 0 && p.x in (xs[0]..xs[1]) && p.y in (ys[0]..ys[1])
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map {
            val vals = regex.find(it)!!.groupValues.toList().drop(1).map { it.toInt() }
            Line(Point(vals[0], vals[1]), Point(vals[2], vals[3]))
        }
        val maxY = input.maxOf { maxOf(it.from.y, it.to.y) }
        val maxX = input.maxOf { maxOf(it.from.x, it.to.x) }

        val points = mutableMapOf<Point, Int>()
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val p = Point(x, y)
                points[p] = 0
            }
        }
        val results = points.entries.parallelStream().map {
            val p = it.key
            p to input.parallelStream().map { if (it.hit(p)) 1 else 0 }.reduce(0) { acc, it -> acc + it }
        }.toList()
        for (it in results) {
            points[it.first] = it.second
        }
        if (print) {
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    val c = points[Point(x, y)].toString().toCharArray().single()
                    print(if (c != '0') c else '.')
                }
                println()
            }
        }
        val xs = points.entries.filter { it.value >= 2 }
        println(xs.size)
    }
}
