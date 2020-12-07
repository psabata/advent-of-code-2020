package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day06.CustomsGroup

fun main() {

    val customsGroups = InputHelper("day06-1.txt").readGroups().map {
        CustomsGroup(it.trim().split(System.lineSeparator()))
    }

    println("Sum of questions with yes across groups: ${customsGroups.sumBy { it.questionsWithYes() }}")
    println("Sum of questions with all yeses across groups: ${customsGroups.sumBy { it.questionsWithAllYeses() }}")

}

object Day06 {

    class CustomsGroup(val answers: List<String>) {

        fun questionsWithYes(): Int {
            return answers
                .flatMapTo(mutableSetOf(), String::toSet)
                .size
        }

        fun questionsWithAllYeses(): Int {
            return answers
                .flatMapTo(mutableListOf(), String::toList)
                .groupBy { it }
                .count { it.value.size == answers.size }
        }

    }

}