object X

val stdin: List<String> = run {
    val name = Thread.currentThread().stackTrace.last().className
    X.javaClass.getResourceAsStream("${name}.txt")!!
        .bufferedReader()
        .lineSequence()
        .filter { it.isNotBlank() }
        .toList()
}
