object Day3 {
    fun Iterable<Boolean>.toInt(): Int = joinToString("") { if (it) "1" else "0" }.toInt(2)

    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map { it.map { it == '1' } }
        val n = input.first().size
        val gammaBits = (0 until n).map { i ->
            val total = input.size
            val ones = input.count { it[i] }
            ones >= total / 2
        }
        val gamma = gammaBits.toInt()
        val epsilon = gammaBits.map { !it }.toInt()
        println(gamma * epsilon)
    }
}
