package info.petrsabata.advent2020

import java.lang.IllegalArgumentException

fun main() {

    val passwordEntries = InputHelper("day02-1.txt").readLines {
        Day02.PasswordEntry(it)
    }

    val sledCompliant = passwordEntries.count { it.isSledCompliant() }

    println("There are Sled compliant $sledCompliant  passwords.")

    val tobogganCompliant = passwordEntries.count { it.isTobogganCompliant() }

    println("There are Toboggan compliant $tobogganCompliant  passwords.")

}

object Day02 {

    class PasswordEntry(entry: String) {

        private companion object {
            val entryPattern = Regex("^(\\d+)-(\\d+) ([a-z]): ([a-z]+)$")
        }

        private val num1: Int
        private val num2: Int
        private val char: Char
        private val password: String

        init {
            val entryValues = entryPattern.matchEntire(entry)?.groupValues
                    ?: throw IllegalArgumentException("Invalid password entry: `$entry`.")

            num1 = entryValues[1].toInt()
            num2 = entryValues[2].toInt()
            char = entryValues[3][0]
            password = entryValues[4]
        }

        fun isSledCompliant(): Boolean {
            return IntRange(num1, num2).contains(password.count { it == char })
        }

        fun isTobogganCompliant(): Boolean {
            return (password[num1 - 1] == char).xor(password[num2 - 1] == char)
        }

    }

}