package info.petrsabata.advent2020

fun main() {

    val memoryGame = MemoryGame(
        InputHelper("day15-1.txt").readText().split(',').map { it.toInt() }
    )

    memoryGame.play(2020)
    memoryGame.play(30000000)

}

class MemoryGame(private val startingNumbers: List<Int>) {

    private val gameInfo = mutableMapOf<Int, MutableList<Int>>()

    fun play(rounds: Int) {

        gameInfo.clear()

        startingNumbers.forEachIndexed { index, startingNumber ->
            update(startingNumber, index + 1)
        }

        var round = startingNumbers.size
        var last = startingNumbers.last()

        do {
            round++

            val prevGameInfo = gameInfo[last]!!

            last = if (prevGameInfo.size == 1) 0 else prevGameInfo.last() - prevGameInfo.secondToLast()

            update(last, round)
        } while (round < rounds)

        println(last)

    }

    private fun update(number: Int, round: Int) {
        gameInfo.getOrPut(number, { mutableListOf() }).apply {
            add(round)
        }
    }

    fun <T> List<T>.secondToLast(): T = get(size - 2)

}

class Day15 {
}