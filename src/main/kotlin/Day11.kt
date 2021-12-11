object Day11 {
    data class Point(val x: Int, val y: Int)
    data class Grid(val width: Int, val height: Int, val samples: IntArray) {
        private fun index(p: Point): Int = width * p.y + p.x
        operator fun get(p: Point): Int = samples[index(p)]
        operator fun set(p: Point, value: Int) = run { samples[index(p)] = value }

        override fun toString(): String = samples.asIterable().chunked(width).joinToString("\n") { line ->
            line.joinToString("") { ('0' + it).toString() }
        }

        val all: Sequence<Point>
            get() = sequence {
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        yield(Point(x, y))
                    }
                }
            }

        fun neighboursOf(p: Point): Sequence<Point> = sequence {
            val xs = 0 until width
            val ys = 0 until height
            val offsets = listOf(
                Point(-1, -1),
                Point(0, -1),
                Point(+1, -1),
                Point(-1, 0),
                Point(+1, 0),
                Point(-1, +1),
                Point(0, +1),
                Point(+1, +1),
            )
            for (offset in offsets) {
                val p2 = p.copy(x = p.x + offset.x, y = p.y + offset.y)
                if (p2.x in xs && p2.y in ys) {
                    yield(p2)
                }
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = Grid(
            width = stdin.first().length,
            height = stdin.size,
            samples = stdin.flatMap { it.map { it - '0' } }.toIntArray()
        )

        fun step(): Int {
            input.all.forEach { input[it] += 1 }
            val flashMap = Grid(
                width = input.width,
                height = input.height,
                samples = IntArray(input.samples.size)
            )
            var totalFlashes = 0
            while (true) {
                var flashes = 0
                input.all.filter { input[it] > 9 && flashMap[it] == 0 }.forEach {
                    flashMap[it] = 1
                    flashes += 1
                    input.neighboursOf(it).forEach { input[it] += 1 }
                }
                totalFlashes += flashes
                if (flashes == 0) {
                    break
                }
            }
            flashMap.all.filter { flashMap[it] != 0 }.forEach { input[it] = 0 }
            return totalFlashes
        }

        var flashes = 0
        println("t=${0}")
        println(input)
        for (t in 1..100) {
            flashes += step()
            println("t=${t}")
            println(input)
        }
        println("#flashes 1..100: $flashes")
        var t = 100
        while (true) {
            t += 1
            if (step() == input.samples.size) {
                break
            }
        }
        println("all flash at t=$t")
    }
}
