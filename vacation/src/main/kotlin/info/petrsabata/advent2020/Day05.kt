package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day05.BoardingPass

fun main() {

    val seatIds = InputHelper("day05-1.txt").readLines {
        BoardingPass(it).getSeatId()
    }.toSet()

    val highestSeatId = seatIds.max()!!

    println("Highest seat ID: $highestSeatId")

    for (seatId in 0..highestSeatId) {
        if (!seatIds.contains(seatId) && seatIds.contains(seatId + 1) && seatIds.contains(seatId - 1)) {
            println("Empty seat: $seatId")
        }
    }

}

object Day05 {

    class BoardingPass(private val seat: String) {

        fun getSeatId(): Int {
            val row = decodeBinaryDirections(seat.substring(0, 7), 'F', 128)
            val seat = decodeBinaryDirections(seat.substring(7, 10), 'L', 8)

            return row * 8 + seat
        }

        private fun decodeBinaryDirections(directions: String, down: Char, start: Int): Int {
            var position = start
            var step = start

            for (direction in directions) {
                step /= 2

                if (direction == down) {
                    position -= step
                }
            }

            return position - 1
        }
    }
}