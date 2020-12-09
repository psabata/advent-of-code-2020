package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day08.GameConsole
import info.petrsabata.advent2020.Day08.GameConsole.ExitCode.*
import info.petrsabata.advent2020.Day08.GameConsole.Instruction
import info.petrsabata.advent2020.Day08.GameConsole.Instruction.*
import java.lang.IllegalArgumentException

fun main() {

    val instructions = InputHelper("day08-1.txt").readLines {
        Instruction.from(it)
    }

    val gameConsole = GameConsole(instructions)

    when(gameConsole.run()) {
        LOOP -> println("Loop detected, acc value: ${gameConsole.acc}")
        COMPLETE -> println("No loop detected.")
    }

    instructions.forEachIndexed { index, instruction ->
        val replaceWith: Instruction = when (instruction) {
            is Nop -> Jmp(instruction.argument)
            is Jmp -> Nop(instruction.argument)
            is Acc -> return@forEachIndexed
        }

        val newInstructions = instructions.toMutableList()
        newInstructions[index] = replaceWith

        val modifiedGameConsole = GameConsole(newInstructions)

        when(modifiedGameConsole.run()) {
            LOOP -> return@forEachIndexed
            COMPLETE -> {
                println("Successful run, acc value: ${modifiedGameConsole.acc}")
                return@main
            }
        }
    }

}

object Day08 {

    class GameConsole(private val instructions: List<Instruction>) {
        var acc = 0
        var position = 0
        private val executed = mutableSetOf<Int>()

        fun run(): ExitCode {
            when {
                executed.contains(position) -> {
                    return LOOP
                }
                position == instructions.size -> {
                    return COMPLETE
                }
                else -> {
                    executed.add(position)
                }
            }

            with (instructions[position]) {
                position += posChange()
                acc += accChange()
            }

            return run()
        }

        enum class ExitCode {
            LOOP,
            COMPLETE
        }

        sealed class Instruction(
            val argument: Int
        ) {

            companion object {

                fun from(instruction: String): Instruction {
                    val split = instruction.split(' ')
                    val argument = split[1].toInt()

                    return when (split[0]) {
                        "nop" -> Nop(argument)
                        "acc" -> Acc(argument)
                        "jmp" -> Jmp(argument)
                        else -> throw IllegalArgumentException("Invalid instruction: $instruction")
                    }
                }
            }

            abstract fun posChange(): Int
            abstract fun accChange(): Int

            class Nop(argument: Int) : Instruction(argument) {
                override fun posChange() = 1
                override fun accChange() = 0
            }

            class Acc(argument: Int) : Instruction(argument) {
                override fun posChange() = 1
                override fun accChange() = argument
            }

            class Jmp(argument: Int) : Instruction(argument) {
                override fun posChange() = argument
                override fun accChange() = 0
            }

        }
    }


}