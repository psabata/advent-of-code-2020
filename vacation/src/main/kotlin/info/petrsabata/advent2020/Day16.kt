package info.petrsabata.advent2020

fun main() {
    val ticketAnalyser = InputHelper("day16-1.txt").readGroups().let { input ->
        TicketAnalyser(
            input[0].trim().split(System.lineSeparator()),
            input[1].trim().split(System.lineSeparator())[1],
            input[2].trim().split(System.lineSeparator()).drop(1)
        )
    }

    println("Ticket scanning error rate: ${ticketAnalyser.ticketsScanningErrorRate()}") // 23044
    println("Your departure multiplication: ${ticketAnalyser.departureMultiplication()}")
}

class TicketAnalyser(
    ticketFields: List<String>,
    yourTicket: String,
    otherTickets: List<String>
) {

    private val ticketFields: Map<String, List<IntRange>>
    private val yourTicket: List<Int>
    private val otherTickets: List<List<Int>>

    init {
        val fieldRegex = Regex("([a-z ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")

        this.ticketFields = ticketFields
            .map { fieldRegex.matchEntire(it)!!.groupValues }
            .associate {
                Pair(
                    it[1],
                    listOf(
                        IntRange(it[2].toInt(), it[3].toInt()),
                        IntRange(it[4].toInt(), it[5].toInt())
                    )
                )
            }
        this.yourTicket = yourTicket.split(',').map { it.toInt() }
        this.otherTickets = otherTickets.map { ticket -> ticket.split(',').map { it.toInt() } }
    }

    fun ticketsScanningErrorRate(): Int {
        return otherTickets.sumBy { ticket ->
            ticket.invalidValues().sum()
        }
    }

    fun departureMultiplication(): Long {
        val validTickets = otherTickets.filter {
            it.invalidValues().isEmpty()
        }.union(listOf(yourTicket))

        val fieldsMapping = mutableMapOf<String, MutableList<Int>>()

        for (fieldIndex in yourTicket.indices) {
            val allFieldValues = validTickets.map { it[fieldIndex] }

            ticketFields.entries.forEach { ticketField ->
                if (
                    allFieldValues.all { fieldValue ->
                        ticketField.value.any { restriction -> restriction.contains(fieldValue) }
                    }
                ) {
                    fieldsMapping.getOrPut(ticketField.key, { mutableListOf() }).apply {
                        add(fieldIndex)
                    }
                }
            }
        }

        val sortedFields = sortedMapOf<Int, String>()

        do {
            var resolvedMapping: Map.Entry<String, List<Int>>? = null

            fieldsMapping.forEach {
                if (it.value.size == 1) {
                    resolvedMapping = it
                    return@forEach
                }
            }

            resolvedMapping?.let {
                val fieldName = it.key
                val fieldIndex = it.value[0]

                sortedFields[fieldIndex] = fieldName

                fieldsMapping.remove(fieldName)
                fieldsMapping.values.forEach { potentialMapping ->
                    potentialMapping.remove(fieldIndex)
                }
            }
        } while (fieldsMapping.isNotEmpty())

        var multiplication = 1L

        sortedFields.filter {
            it.value.startsWith("departure")
        }.forEach { departureField ->
            val yourValue = yourTicket[departureField.key]
            multiplication *= yourValue
        }

        return multiplication
    }

    private fun List<Int>.invalidValues(): List<Int> {
        return filter {
            !ticketFields.values.any { restrictions ->
                restrictions.any { restriction ->
                    restriction.contains(it)
                }
            }
        }
    }

}