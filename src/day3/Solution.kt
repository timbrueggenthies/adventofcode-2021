package day3

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        val gamma = mutableListOf<Bit>()
        val epsilon = mutableListOf<Bit>()
        val rows = extractRows(input)
        rows.first().indices.map { column ->
            val mostCommonBit = rows.mostCommonBit(column)
            gamma += mostCommonBit
            epsilon += -mostCommonBit
        }
        return gamma.toDecimal() * epsilon.toDecimal()
    }

    fun part2(input: List<String>): Int {
        val rows = extractRows(input)
        var oxygenRows = rows
        var column = 0
        while(oxygenRows.size > 1) {
            val mostCommonBit = oxygenRows.mostCommonBit(column)
            oxygenRows = oxygenRows.filter { it[column] == mostCommonBit }
            column++
        }
        var co2Rows = rows
        column = 0
        while(co2Rows.size > 1) {
            val leastCommonBit = -co2Rows.mostCommonBit(column)
            co2Rows = co2Rows.filter { it[column] == leastCommonBit }
            column++
        }
        return oxygenRows.single().toDecimal() * co2Rows.single().toDecimal()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day3")
    check(part2(testInput) == 230)

    val input = readInput("day3")
    println(part1(input))
    println(part2(input))
}

private fun List<List<Bit>>.mostCommonBit(column: Int): Bit {
    var ones = 0
    var zeros = 0
    forEach { row ->
        if (row[column] == Bit.Zero) zeros++ else ones++
    }
    return if (zeros > ones) Bit.Zero else Bit.One
}

private fun extractRows(input: List<String>): List<List<Bit>> {
    return input.map { row ->
        row.map { if (it == '0') Bit.Zero else Bit.One }
    }
}

fun List<Bit>.toDecimal(): Int {
    return map { it.char }.joinToString(separator = "").toInt(radix = 2)
}

operator fun Bit.unaryMinus() = if (this == Bit.Zero) Bit.One else Bit.Zero

enum class Bit(val char: Char) {
    One('1'), Zero('0')
}
