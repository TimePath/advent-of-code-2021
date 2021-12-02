object Day1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map { it.toInt() }
        val increments = input.windowed(2).map { (a, b) -> b > a }
        println(increments.count { it })
    }
}
