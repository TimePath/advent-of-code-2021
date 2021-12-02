object Day2 {
    sealed interface Command {
        data class Horizontal(val amount: Int) : Command
        data class Depth(val amount: Int) : Command
    }

    class State(var horizontal: Int, var depth: Int) {
        fun apply(command: Command) = when (command) {
            is Command.Horizontal -> {
                horizontal += command.amount
            }
            is Command.Depth -> {
                depth += command.amount
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = stdin.map {
            val (dir, nStr) = it.split(' ')
            val n = nStr.toInt()
            when (dir) {
                "forward" -> Command.Horizontal(n)
                "down" -> Command.Depth(n)
                "up" -> Command.Depth(-n)
                else -> throw NoWhenBranchMatchedException()
            }
        }
        val state = State(0, 0)
        input.forEach { state.apply(it) }
        println(state.depth * state.horizontal)
    }
}
