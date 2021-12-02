object Day1_2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map { it.toInt() }
        val sums = input.windowed(3).map { (a, b, c) -> a + b + c }
        val increments = sums.windowed(2).map { (a, b) -> b > a }
        println(increments.count { it })
    }
}
