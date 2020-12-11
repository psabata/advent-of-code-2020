package info.petrsabata.advent2020

fun main() {
    val adapters = InputHelper("day10-1.txt").readIntLines().toMutableList()

    adapters.add(0) // charging outlet
    adapters.add(adapters.max()!! + 3) // device adapter

    adapters.sort()

    var oneJoltDiff = 0
    var threeJoltsDiff = 0

    adapters.subList(0, adapters.size - 1).forEachIndexed { index, joltage ->
        when (adapters[index + 1] - joltage) {
            1 -> oneJoltDiff++
            3 -> threeJoltsDiff++
            else -> {
            }
        }
    }

    println("1-jolt diff  count * 3 jolts diff count = ${oneJoltDiff * threeJoltsDiff}")


    val combinations = Day10.AdapterOrganizer(adapters).arrange()

    println("There are $combinations ways to plug in your adapters.")

}

class Day10 {

    class AdapterOrganizer(adapters: List<Int>) {

        private val sublists = mutableListOf<List<Int>>()

        init {
            var start = 0

            for (i in 0..adapters.size - 2) {
                val stop = i + 1

                if (adapters[stop] - adapters[i] == 3) {
                    sublists.add(adapters.subList(start, stop))
                    start = stop
                }
            }

            sublists.add(adapters.subList(start, adapters.size))
        }

        fun arrange(): Long {
            return sublists.fold(1L, { acc: Long, adapters: List<Int> ->
                acc * arrange(adapters)
            })
        }

        private fun arrange(adapters: List<Int>): Long {
            if (adapters.size < 2) {
                return 1L
            }

            var combinations = 0L

            for (i in 1 until adapters.size) {
                if (adapters[i] - adapters[0] <= 3) {
                    combinations += arrange(adapters.subList(i, adapters.size))
                } else {
                    break
                }
            }

            return combinations
        }

    }

}