package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day04.Passport.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day04Test {

    @Test
    fun whenBirthYearIsValid_thenIsValidIsTrue() {
        assertThat(BirthYear("1955").isValid(), `is`(true))
    }

    @Test
    fun whenIssueYearIsValid_thenIsValidIsTrue() {
        assertThat(IssueYear("2015").isValid(), `is`(true))
    }

    @Test
    fun whenExpirationYearIsValid_thenIsValidIsTrue() {
        assertThat(ExpirationYear("2025").isValid(), `is`(true))
    }

    @Test
    fun whenHeightIsValid_thenIsValidIsTrue() {
        assertThat(Height("170cm").isValid(), `is`(true))
        assertThat(Height("60in").isValid(), `is`(true))
    }

    @Test
    fun whenHairColorIsValid_thenIsValidIsTrue() {
        assertThat(HairColor("#abc123").isValid(), `is`(true))
    }

    @Test
    fun whenEyeColorIsValid_thenIsValidIsTrue() {
        assertThat(EyeColor("amb").isValid(), `is`(true))
    }

    @Test
    fun whenPassportIdIsValid_thenIsValidIsTrue() {
        assertThat(PassportId("012345678").isValid(), `is`(true))
    }

}