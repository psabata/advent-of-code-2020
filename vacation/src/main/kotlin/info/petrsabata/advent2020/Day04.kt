package info.petrsabata.advent2020

fun main() {

    val passports = InputHelper("day04-1.txt").readText()
            .split(Regex("^\\s*$", RegexOption.MULTILINE))
            .map { it.replace(System.lineSeparator(), " ").trim() }
            .map { Day04.Passport(it) }

    println("There are ${passports.count { it.isValid }} passports without missing fields.")
    println("There are ${passports.count { it.isValidStrict }} passports with valid fields.")

}

object Day04 {

    class Passport(input: String) {

        private companion object {
            const val BIRTH_YEAR = "byr"
            const val ISSUE_YEAR = "iyr"
            const val EXPIRATION_YEAR = "eyr"
            const val HEIGHT = "hgt"
            const val HAIR_COLOR = "hcl"
            const val EYE_COLOR = "ecl"
            const val PASSPORT_ID = "pid"
            const val COUNTRY_ID = "cid"
        }

        open class PassportField(private val value: String?, private val validator: (String) -> Boolean) {
            fun isEmpty(): Boolean = value.isNullOrEmpty()
            fun isValid(): Boolean = !isEmpty() && validator.invoke(value!!)
        }

        class BirthYear(value: String?) : PassportField(value, {
            val year = it.toIntOrNull()
            year != null && year >= 1920 && year <= 2002
        })

        class IssueYear(value: String?) : PassportField(value, {
            val year = it.toIntOrNull()
            year != null && year >= 2010 && year <= 2020
        })

        class ExpirationYear(value: String?) : PassportField(value, {
            val year = it.toIntOrNull()
            year != null && year >= 2020 && year <= 2030
        })

        class Height(value: String?) : PassportField(value, {
            when {
                it.endsWith("cm") -> {
                    val cm = it.substringBeforeLast("cm").toIntOrNull()
                    cm != null && cm >= 150 && cm <= 193
                }
                it.endsWith("in") -> {
                    val `in` = it.substringBeforeLast("in").toIntOrNull()
                    `in` != null && `in` >= 59 && `in` <= 76
                }
                else -> false
            }
        })

        class HairColor(value: String?) : PassportField(value, {
            pattern.matches(it)
        }) {
            companion object {
                val pattern = Regex("#[0-9a-f]{6}")
            }
        }

        class EyeColor(value: String?) : PassportField(value, {
            colors.contains(it)
        }) {
            companion object {
                val colors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            }
        }

        class PassportId(value: String?) : PassportField(value, {
            pattern.matches(it)
        }) {
            companion object {
                val pattern = Regex("[0-9]{9}")
            }
        }

        class CountryId(value: String?) : PassportField(value, {
            true
        })

        private val birthYear: BirthYear
        private val issueYear: IssueYear
        private val expirationYear: ExpirationYear
        private val height: Height
        private val hairColor: HairColor
        private val eyeColor: EyeColor
        private val passportId: PassportId
        private val countryId: CountryId


        init {
            val passportFields = mutableMapOf<String, String>()

            input
                    .split(" ")
                    .map { it.split(":") }
                    .forEach { passportFields[it[0]] = it[1] }

            birthYear = BirthYear(passportFields[BIRTH_YEAR])
            issueYear = IssueYear(passportFields[ISSUE_YEAR])
            expirationYear = ExpirationYear(passportFields[EXPIRATION_YEAR])
            height = Height(passportFields[HEIGHT])
            hairColor = HairColor(passportFields[HAIR_COLOR])
            eyeColor = EyeColor(passportFields[EYE_COLOR])
            passportId = PassportId(passportFields[PASSPORT_ID])
            countryId = CountryId(passportFields[COUNTRY_ID])
        }

        val isValid: Boolean
            get() = !birthYear.isEmpty()
                    && !issueYear.isEmpty()
                    && !expirationYear.isEmpty()
                    && !height.isEmpty()
                    && !hairColor.isEmpty()
                    && !eyeColor.isEmpty()
                    && !passportId.isEmpty()

        val isValidStrict: Boolean
            get() = birthYear.isValid()
                    && issueYear.isValid()
                    && expirationYear.isValid()
                    && height.isValid()
                    && hairColor.isValid()
                    && eyeColor.isValid()
                    && passportId.isValid()
    }
}