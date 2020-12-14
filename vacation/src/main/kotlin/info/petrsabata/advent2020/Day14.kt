package info.petrsabata.advent2020

import info.petrsabata.advent2020.DockingProgram.Version.DECODER_1
import info.petrsabata.advent2020.DockingProgram.Version.DECODER_2
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

fun main() {

    val dockingProgram = DockingProgram(
        InputHelper("day14-1.txt").readLines {
            DockingProgram.Instruction.from(it)
        }
    )

    println("Docking program -> execute1 -> ${dockingProgram.execute(DECODER_1)}") // 15919415426101
    println("Docking program -> execute2 -> ${dockingProgram.execute(DECODER_2)}") // 3443997590975

}

class DockingProgram(private val instructions: List<Instruction>) {

    companion object {
        const val BIT_LENGTH = 36
    }

    var memory = mutableMapOf<Long, Long>()

    fun execute(version: Version): Long {
        memory.clear()

        var mask: Instruction.Mask = instructions[0] as Instruction.Mask

        for (instruction in instructions.subList(1, instructions.size)) {
            when (instruction) {
                is Instruction.Mask -> {
                    mask = instruction
                }
                is Instruction.Write -> {
                    when (version) {
                        DECODER_1 -> {
                            memory[instruction.address] = mask.apply(instruction.value)
                        }
                        DECODER_2 -> {
                            for (floatingAddress in mask.applyFloating(instruction.address)) {
                                memory[floatingAddress] = instruction.value
                            }
                        }
                    }
                }
            }
        }

        return memory.values.sum()
    }

    sealed class Instruction {

        companion object {

            private val MASK = Regex("mask = ([X\\d]+)")
            private val WRITE = Regex("mem\\[(\\d+)\\] = (\\d+)")

            fun from(input: String): Instruction {

                return when {
                    input.startsWith("mask") -> {
                        val matchResult = MASK.matchEntire(input)!!
                        Mask(matchResult.groupValues[1])
                    }
                    input.startsWith("mem") -> {
                        val matchResult = WRITE.matchEntire(input)!!
                        Write(matchResult.groupValues[1].toLong(), matchResult.groupValues[2].toLong())
                    }
                    else -> throw IllegalArgumentException("Invalid instruction: $input")
                }

            }
        }

        data class Mask(val mask: String) : Instruction() {
            fun apply(number: Long): Long {
                val binaryNumber = number.toBinaryString()

                val result = StringBuilder()

                for (i in 0 until BIT_LENGTH) {
                    if (mask[i] != 'X') {
                        result.append(mask[i])
                    } else {
                        result.append(binaryNumber[i])
                    }
                }

                return java.lang.Long.valueOf(result.toString(), 2)
            }

            fun applyFloating(address: Long): List<Long> {
                val binaryAddress = address.toBinaryString()

                val result = StringBuilder()

                for (i in 0 until BIT_LENGTH) {
                    when (mask[i]) {
                        '0' -> result.append(binaryAddress[i])
                        '1' -> result.append('1')
                        'X' -> result.append('X')
                    }
                }

                return generateFloating(listOf(result.toString())).map {
                    java.lang.Long.valueOf(it, 2)
                }
            }

            private fun generateFloating(floatingAddresses: List<String>): List<String> {
                if (!floatingAddresses[0].contains('X')) {
                    return floatingAddresses
                }

                val result = mutableListOf<String>()

                for (floatingAddress in floatingAddresses) {
                    result.add(floatingAddress.replaceFirst('X', '0'))
                    result.add(floatingAddress.replaceFirst('X', '1'))
                }

                return generateFloating(result)
            }
        }

        data class Write(val address: Long, val value: Long) : Instruction()

    }

    enum class Version {
        DECODER_1,
        DECODER_2
    }

}

private fun Long.toBinaryString(): String {
    return java.lang.Long.toBinaryString(this).padStart(DockingProgram.BIT_LENGTH, '0')
}