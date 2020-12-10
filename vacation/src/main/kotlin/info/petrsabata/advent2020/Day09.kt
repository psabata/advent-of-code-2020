package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day09.DataPort
import java.lang.IllegalArgumentException

fun main() {

    val dataPort = DataPort(
        InputHelper("day09-1.txt").readLines {
            it.toLong()
        }
    )

    val invalidNumber: Long = dataPort.findInvalid()

    println("Invalid number: $invalidNumber")

    val encryptionWeakness = dataPort.findWeakness(invalidNumber)

    println("Encryption weakness: $encryptionWeakness")
}


object Day09 {

    class DataPort(private val output: List<Long>) {

        private companion object {
            const val PREAMBLE_SIZE = 25
        }

        fun findInvalid(): Long {
            for (i in PREAMBLE_SIZE until output.size) {
                if (!checkSum(output[i], output.subList(i - PREAMBLE_SIZE, i))) {
                    return output[i]
                }
            }

            throw IllegalArgumentException("No invalid number found for output $output.")
        }

        private fun checkSum(number: Long, preamble: List<Long>): Boolean {
            for (i in 0 until preamble.size - 1) {
                for (j in i + 1 until preamble.size) {
                    if (preamble[i] + preamble[j] == number) {
                        return true
                    }
                }
            }

            return false
        }

        fun findWeakness(invalidNumber: Long): Long {
            for (i in output.indices) {
                var sum = output[i]
                var j = i + 1

                while (sum < invalidNumber) {
                    sum += output[j]

                    if (sum == invalidNumber) {
                        val contiguousSet = output.subList(i, j + 1)
                        return contiguousSet.min()!! + contiguousSet.max()!!
                    }

                    j++
                }
            }

            throw IllegalArgumentException("No weakness found for number $invalidNumber.")
        }

    }

}