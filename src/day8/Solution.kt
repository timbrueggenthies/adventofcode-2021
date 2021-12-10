package day8

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        val digitInfos = extractDigits(input)
        val uniqueLengthDigits = setOf(2, 3, 4, 7)
        return digitInfos.sumOf { digitInfo -> digitInfo.displayedDigits.count { it.size in uniqueLengthDigits } }
    }

    fun part2(input: List<String>): Int {
        val digitInfos = extractDigits(input)
        return digitInfos.sumOf { info ->
            val newDigits1 = info.digits.first { digit -> digit.size == 2 }
            val newDigits4 = info.digits.first { digit -> digit.size == 4 }
            val newDigits7 = info.digits.first { digit -> digit.size == 3 }
            val newDigits8 = info.digits.first { digit -> digit.size == 7 }

            val newDigits3 = info.digits.first { digit -> digit.size == 5 && newDigits1.all { it in digit } }
            val newDigits9 = info.digits.first { digit -> digit.size == 6 && newDigits4.all { it in digit } }
            val newDigits0 = info.digits.first { digit -> digit.size == 6 && newDigits7.all { it in digit } && !newDigits4.all { it in digit }}
            val newDigits6 = info.digits.first { digit -> digit.size == 6 && digit != newDigits9 && digit != newDigits0 }

            val in2 = newDigits8.filter { it !in newDigits3 }.single { it !in newDigits4 }
            val newDigits2 = info.digits.first { digit -> digit.size == 5 && in2 in digit }
            val newDigits5 = info.digits.first { digit -> digit.size == 5 && digit != newDigits2 && digit != newDigits3 }

            val newDigits = mapOf(
                newDigits0.joinToString(separator = "") to 0,
                newDigits1.joinToString(separator = "") to 1,
                newDigits2.joinToString(separator = "") to 2,
                newDigits3.joinToString(separator = "") to 3,
                newDigits4.joinToString(separator = "") to 4,
                newDigits5.joinToString(separator = "") to 5,
                newDigits6.joinToString(separator = "") to 6,
                newDigits7.joinToString(separator = "") to 7,
                newDigits8.joinToString(separator = "") to 8,
                newDigits9.joinToString(separator = "") to 9,
            )
            val a = newDigits[info.displayedDigits[0].joinToString(separator = "")]!! * 1000
            val b = newDigits[info.displayedDigits[1].joinToString(separator = "")]!! * 100
            val c = newDigits[info.displayedDigits[2].joinToString(separator = "")]!! * 10
            val d = newDigits[info.displayedDigits[3].joinToString(separator = "")]!! * 1

            a + b + c + d
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day8")
    check(part2(testInput) == 61229)

    val input = readInput("day8")
    println(part1(input))
    println(part2(input))
}

private fun extractDigits(input: List<String>): List<DisplayInformation> {
    val regex = Regex("\\w*")
    return input.map { row ->
        val matches = regex.findAll(row)
            .map { it.value }
            .filter { it.isNotBlank() }
            .map { it.toList().sorted() }
            .toList()
        val digits = matches.take(10)
        val displayedDigits = matches.drop(10)
        DisplayInformation(digits, displayedDigits)
    }
}

data class DisplayInformation(
    val digits: List<List<Char>>,
    val displayedDigits: List<List<Char>>
)
