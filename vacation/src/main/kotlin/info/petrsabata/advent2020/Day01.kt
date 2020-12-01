package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day01.ExpenseReport
import java.lang.IllegalArgumentException

fun main() {

    val expenseReport = ExpenseReport(InputHelper("day01-1.txt").readIntLines())

    println("Fix for two: " + expenseReport.fixTwo())
    println("Fix for three: " + expenseReport.fixThree())

}

object Day01 {

    class ExpenseReport(private val expenses: List<Int>) {

        fun fixTwo(): Int {

            for (i in 0 until expenses.size - 1) {
                for (j in i + 1 until expenses.size) {
                    if (expenses[i] + expenses[j] == 2020) {
                        return expenses[i] * expenses[j]
                    }
                }
            }

            throw IllegalArgumentException("No two expenses that sums into 2020 in $expenses.")
        }

        fun fixThree(): Int {

            for (i in 0 until expenses.size - 2) {
                for (j in i + 1 until expenses.size - 1) {
                    val partialSum = expenses[i] + expenses[j]

                    if (partialSum >= 2020) {
                        continue
                    }

                    for (k in j + 1 until expenses.size) {
                        if (partialSum + expenses[k] == 2020) {
                            return expenses[i] * expenses[j] * expenses[k]
                        }
                    }
                }
            }

            throw IllegalArgumentException("No three expenses that sums into 2020 in $expenses.")
        }
    }

}