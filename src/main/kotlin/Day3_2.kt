object Day3_2 {
    fun Iterable<Boolean>.toInt(): Int = joinToString("") { if (it) "1" else "0" }.toInt(2)

    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map { it.map { it == '1' } }
        val o2 = filter(input, bias = true, default = true).toInt()
        val co2 = filter(input, bias = false, default = false).toInt()
        println(o2 * co2)
    }

    fun filter(input: List<List<Boolean>>, bias: Boolean, default: Boolean): List<Boolean> {
        var ret = input
        val n = input.first().size
        repeat(n) { i ->
            val ones = ret.count { it[i] }
            val zeros = ret.size - ones
            val mask = when {
                ones > zeros -> bias
                zeros > ones -> !bias
                else -> default
            }
            ret = ret.filter { it[i] == mask }
            if (ret.size == 1) {
                return ret.single()
            }
        }
        return ret.single()
    }
}
