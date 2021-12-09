object Day9 {
    data class Point(val x: Int, val y: Int)
    data class Grid(val width: Int, val height: Int, val samples: IntArray) {
        private fun index(p: Point): Int = width * p.y + p.x
        operator fun get(p: Point): Int = samples[index(p)]
        operator fun set(p: Point, value: Int) = run { samples[index(p)] = value }

        override fun toString(): String = samples.asIterable().chunked(width).joinToString("\n") { line ->
            line.joinToString("") { (' ' + it).toString() }
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
            for (offset in listOf(Point(0, -1), Point(0, +1), Point(-1, 0), Point(+1, 0))) {
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
        val lows = input.all.mapNotNull {
            val h = input[it]
            val low = input.neighboursOf(it).all {
                val h2 = input[it]
                h < h2
            }
            it.takeIf { low }
        }.toList()
        val risks = lows.map { 1 + input[it] }
        println(risks.sum())

        val basinMap = Grid(
            width = input.width,
            height = input.height,
            samples = IntArray(input.samples.size) { 0 }
        )
        val land = 9
        lows.forEachIndexed { i, p ->
            val x = 1 + i
            val queue = ArrayDeque<Point>()
            queue.add(p)
            while (queue.isNotEmpty()) {
                val it = queue.removeFirst()
                val h = input[it]
                if (h >= land) {
                    continue
                }
                for (n in input.neighboursOf(it).filter { basinMap[it] == 0 }) {
                    queue.addLast(n)
                }
                basinMap[it] = x
            }
        }
        val basins = basinMap.samples
            .asSequence()
            .groupingBy { it }
            .eachCount()
            .map { it.value }
            .sortedDescending()
            .drop(1)
        println(basins.take(3).reduce { a, b -> a * b })
        println(basinMap)
    }
}
