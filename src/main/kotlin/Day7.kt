import Day7.CostFunction
import kotlin.math.absoluteValue

object Day7 {
    fun interface CostFunction : (Int) -> Int {
        operator fun invoke(pos1: Int, pos2: Int) = this((pos1 - pos2).absoluteValue)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.first().split(",").map { it.toInt() }
        val costFunctions = listOf(
            CostFunction { it },
            CostFunction { (it * it + it) / 2 },
        )
        for (cost in costFunctions) {
            val mid = leastDistant(input, cost)
            println(input.sumOf { cost(it, mid) })
        }
    }

    private fun leastDistant(positions: List<Int>, cost: CostFunction): Int {
        val distanceCosts = positions.map { self ->
            val distances = positions.map { other -> cost(self, other) }
            distances.sum()
        }
        return positions.zip(distanceCosts).minByOrNull { it.second }!!.first
    }
}
