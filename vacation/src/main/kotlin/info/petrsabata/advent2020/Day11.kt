package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day11.SeatMap

fun main() {

    val input = InputHelper("day11-1.txt").readLines()

    val seatMap1 = SeatMap(input)
    seatMap1.simulate1()

    println("There are ${seatMap1.occupiedSeats()} occupied seats when checking neighbours.")

    val seatMap2 = SeatMap(input)
    seatMap2.simulate2()

    println("There are ${seatMap2.occupiedSeats()} occupied seats when checking directions.")
}

class Day11 {

    class SeatMap(input: List<String>) {

        private var seatMap: List<String> = input

        private val width: Int
            get() = seatMap[0].length

        private val height: Int
            get() = seatMap.size

        fun simulate1() {
            val newSeatMap = seatMap.map { it.toCharArray() }

            for (x in 0 until width) {
                for (y in 0 until height) {
                    when (seatMap[y][x]) {
                        FLOOR -> {
                        }
                        EMPTY_SEAT -> if (occupiedNeighbours(x, y) == 0) {
                            newSeatMap[y][x] = OCCUPIED_SEAT
                        }
                        OCCUPIED_SEAT -> if (occupiedNeighbours(x, y) >= 4) {
                            newSeatMap[y][x] = EMPTY_SEAT
                        }
                    }
                }
            }

            val transformed = newSeatMap.map { String(it) }

            if (seatMap == transformed) {
                return
            }

            seatMap = transformed
            simulate1()
        }

        fun simulate2() {
            val newSeatMap = seatMap.map { it.toCharArray() }

            for (x in 0 until width) {
                for (y in 0 until height) {
                    when (seatMap[y][x]) {
                        FLOOR -> {
                        }
                        EMPTY_SEAT -> if (occupiedDirections(x, y) == 0) {
                            newSeatMap[y][x] = OCCUPIED_SEAT
                        }
                        OCCUPIED_SEAT -> if (occupiedDirections(x, y) >= 5) {
                            newSeatMap[y][x] = EMPTY_SEAT
                        }
                    }
                }
            }

            val transformed = newSeatMap.map { String(it) }

            if (seatMap == transformed) {
                return
            }

            seatMap = transformed
            simulate2()
        }

        fun occupiedSeats(): Int {
            return seatMap.sumBy { it.count { it == OCCUPIED_SEAT } }
        }

        private fun occupiedNeighbours(x: Int, y: Int): Int {
            var occupied = 0

            for (direction in Directions.values()) {
                val xx = x + direction.dx
                val yy = y + direction.dy

                if (outsideBoundaries(xx, yy)) {
                    continue
                }

                if (seatMap[yy][xx] == OCCUPIED_SEAT) {
                    occupied++
                }
            }

            return occupied
        }

        private fun occupiedDirections(x: Int, y: Int): Int {
            var occupied = 0

            directions@ for (direction in Directions.values()) {
                var xx = x
                var yy = y

                do {
                    xx += direction.dx
                    yy += direction.dy

                    if (outsideBoundaries(xx, yy)) {
                        continue@directions
                    }

                    when (seatMap[yy][xx]) {
                        OCCUPIED_SEAT -> {
                            occupied++
                            continue@directions
                        }
                        EMPTY_SEAT -> {
                            continue@directions
                        }
                    }
                } while (true)
            }

            return occupied
        }

        private fun outsideBoundaries(x: Int, y: Int): Boolean {
            return (x < 0 || x >= width) || (y < 0 || y >= height)
        }

//        private fun print() {
//            seatMap.forEach { println(it) }
//            println("- - -")
//        }

        private enum class Directions(val dx: Int, val dy: Int) {
            NORTH(0, -1),
            NORTH_EAST(1, -1),
            EAST(1, 0),
            SOUTH_EAST(1, 1),
            SOUTH(0, 1),
            SOUTH_WEST(-1, 1),
            WEST(-1, 0),
            NORTH_WEST(-1, -1)
        }

        private companion object {
            const val FLOOR = '.'
            const val EMPTY_SEAT = 'L'
            const val OCCUPIED_SEAT = 'X'
        }

    }
}