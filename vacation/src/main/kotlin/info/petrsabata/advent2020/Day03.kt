package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day03.Slope
import info.petrsabata.advent2020.Day03.TobogganHill
import info.petrsabata.advent2020.Day03.navigate

fun main() {

    val tobogganHill = TobogganHill(
            InputHelper("day03-1.txt").readLines()
    )

    val slopes = listOf(
            Slope(1, 1),
            Slope(3, 1),
            Slope(5, 1),
            Slope(7, 1),
            Slope(1, 2)
    )

    val treesMultiplied = slopes.map { slope ->
        val trees = navigate(tobogganHill, slope)
        println("$slope -> $trees ")
        trees.toLong()
    }.reduce { multiplied, current ->
        multiplied * current
    }

    println("Trees multiplied: $treesMultiplied")
}

object Day03 {

    fun navigate(tobogganHill: TobogganHill, slope: Slope): Int {
        var x = 0
        var y = 0
        var trees = 0

        while (y < tobogganHill.length) {
            if (tobogganHill.checkTree(x, y)) {
                trees++
            }

            x += slope.right
            y += slope.down
        }

        return trees
    }

    data class TobogganHill(val geography: List<String>) {
        val length: Int
            get() = geography.size

        private val width: Int
            get() = geography[0].length

        fun checkTree(x: Int, y: Int): Boolean {
            return geography[y][x % width] == '#'
        }

    }

    data class Slope(val right: Int, val down: Int)

}