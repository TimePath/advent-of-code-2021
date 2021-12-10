object Day10 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map { it }
        val pairings = listOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>',
        )
        val charToIndex = sequence {
            pairings.forEachIndexed { i, it ->
                yield(it.first to i)
                yield(it.second to i)
            }
        }.toMap()
        val scoreCorrupt = mapOf(
            charToIndex[')']!! to 3L,
            charToIndex[']']!! to 57L,
            charToIndex['}']!! to 1197L,
            charToIndex['>']!! to 25137L,
        )
        val scoreIncomplete = mapOf(
            charToIndex[')']!! to 1L,
            charToIndex[']']!! to 2L,
            charToIndex['}']!! to 3L,
            charToIndex['>']!! to 4L,
        )
        val charToDir = sequence {
            pairings.forEach {
                yield(it.first to +1)
                yield(it.second to -1)
            }
        }.toMap()

        data class Points(val corrupt: Long = 0, val incomplete: Long = 0)

        val scores = input.map { line ->
            val state = ArrayDeque<Int>()
            for (c in line) {
                val idx = charToIndex[c]!!
                val dir = charToDir[c]!!
                when {
                    dir > 0 -> state.addLast(idx)
                    dir < 0 -> if (state.removeLast() != idx) {
                        val points = scoreCorrupt[idx]!!
                        return@map Points(corrupt = points)
                    }
                }
            }
            val points = state.asReversed().fold(0L) { acc, it -> acc * 5 + scoreIncomplete[it]!! }
            Points(incomplete = points)
        }
        println(scores.mapNotNull { it.corrupt.takeIf { it != 0L } }.sum())
        println(scores.mapNotNull { it.incomplete.takeIf { it != 0L } }.sortedDescending().let { it[it.size / 2] })
    }
}
