package info.petrsabata.advent2020

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

class Day01Test {

    @Test
    fun whenThereAreTwoExpensesToFix_thenCorrectEntriesAreIdentified() {

        // given

        val expenses = listOf(1, 2, 1000, 1020)


        // when

        val fixedReport = Day01.ExpenseReport(expenses).fixTwo()

        // then

        MatcherAssert.assertThat(fixedReport, `is`(1000 * 1020))

    }

    @Test
    fun whenThereAreThreeExpensesToFix_thenCorrectEntriesAreIdentified() {

        // given

        val expenses = listOf(1, 10, 100, 1000, 1010, 1020)


        // when

        val fixedReport = Day01.ExpenseReport(expenses).fixThree()

        // then

        MatcherAssert.assertThat(fixedReport, `is`(10 * 1000 * 1010))

    }

}