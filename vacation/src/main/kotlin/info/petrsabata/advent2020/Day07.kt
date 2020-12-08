package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day07.BagRegulation
import java.lang.IllegalArgumentException

fun main() {

    val bagRegulations = InputHelper("day07-1.txt").readLines {
        BagRegulation(it)
    }

    val bagRegulationsMap = bagRegulations.groupBy {
        it.bagColor
    }.mapValues {
        if (it.value.size != 1) {
            throw IllegalArgumentException("More than one rule for color ${it.key}.")
        }
        it.value[0]
    }

    val colorsWithEnclosedShinyGold = bagRegulations.count {
        it.containsShinyGold(bagRegulationsMap)
    }

    println("Bag colors with enclosed shiny gold: $colorsWithEnclosedShinyGold")

    val bagsEnclosedInShinyGold = bagRegulationsMap["shiny gold"]!!.enclosedCount(bagRegulationsMap)

    println("Bags enclosed in shiny gold: $bagsEnclosedInShinyGold")
}

object Day07 {

    class BagRegulation(line: String) {

        private companion object {
            val LINE_PATTERN = Regex("([0-9]* ?[a-z]+ [a-z]+) bags?")
            val RULE_PATTERN = Regex("([0-9]+) ([a-z]+ [a-z]+)")
        }

        val bagColor: String
        private val enclosed: List<Pair<Int, String>>

        init {
            val rules = LINE_PATTERN.findAll(line).map { it.groupValues[1] }.toList()

            bagColor = rules[0]

            enclosed = mutableListOf()

            for (rule in rules.subList(1, rules.size)) {
                val matchedRule = RULE_PATTERN.matchEntire(rule)

                if (matchedRule != null) {
                    val groupValues = matchedRule.groupValues
                    enclosed.add(Pair(groupValues[1].toInt(), groupValues[2]))
                }
            }
        }

        fun containsShinyGold(bagRegulationsMap: Map<String, BagRegulation>): Boolean {
            val enclosedColors = enclosed.map { it.second }

            if (enclosedColors.contains("shiny gold")) {
                return true
            }

            for (color in enclosedColors) {
                if (bagRegulationsMap[color]!!.containsShinyGold(bagRegulationsMap)) {
                    return true
                }
            }

            return false
        }

        fun enclosedCount(bagRegulationsMap: Map<String, BagRegulation>): Int {
            return when {
                enclosed.isEmpty() -> {
                    0
                }
                else -> enclosed.sumBy {
                    it.first + (it.first * bagRegulationsMap[it.second]!!.enclosedCount(bagRegulationsMap))
                }
            }

        }

        override fun toString(): String {
            return "BagRegulation(bagColor='$bagColor', enclosed=$enclosed)"
        }

    }

}