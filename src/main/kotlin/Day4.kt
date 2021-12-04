object Day4 {
    val dimensions = 5

    @JvmStatic
    fun main(args: Array<String>) {
        val values = stdin.first().split(",").map { it.toInt() }
        val boards = stdin.drop(1).chunked(dimensions).map { board ->
            board.flatMap { row ->
                row.split(" +".toRegex())
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
        }.mapIndexed { i, it -> Board(it, "#${1 + i}") }
        var remainingBoards = boards
        for (n in values) {
            val (winners, losers) = remainingBoards.partition { it.mark(n); it.won }
            for (it in winners) {
                println("${it.name}: ${it.score(n)}")
            }
            remainingBoards = losers
        }
    }

    class Board(private val values: List<Int>, val name: String) {
        private val rows: List<List<Int>> get() = rowIndices.map { it.map { i -> values[i] } }
        private val cols: List<List<Int>> get() = colIndices.map { it.map { i -> values[i] } }
        private val marks: MutableSet<Int> = mutableSetOf()
        fun mark(value: Int): Unit = marks.add(value).let {}
        val won: Boolean get() = rows.any { it.all { v -> v in marks } } || cols.any { it.all { v -> v in marks } }
        fun score(value: Int): Int = value * values.sumOf { if (it in marks) 0 else it }
        private val rowIndices: List<List<Int>> = (0 until dimensions).map { r ->
            (0 until dimensions).map { c -> dimensions * r + c }
        }
        private val colIndices: List<List<Int>> = (0 until dimensions).map { c ->
            (0 until dimensions).map { r -> dimensions * r + c }
        }
    }
}
