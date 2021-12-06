object Day6 {
    class DecayBag(private val size: Int) {
        private var offset: Int = 0
        fun decay() = run { offset += 1 }

        private val arr = LongArray(size) { 0 }
        operator fun get(i: Int): Long = arr[(offset + i) % size]
        operator fun set(i: Int, v: Long) = run { arr[(offset + i) % size] = v }
        fun sum() = arr.sumOf { it }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.single().split(",").map { it.toInt() }
        val bag = DecayBag(8 + 1)
        for (it in input) {
            bag[it] += 1.toLong()
        }
        for (t0 in 0 until 256) {
            val births = bag[0].also { bag[0] = 0 }
            bag.decay()
            bag[6] += births // gave birth
            bag[8] += births // new born
            val t1 = t0 + 1
            println("After $t1 days: ${bag.sum()}")
        }
    }
}
