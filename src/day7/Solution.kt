package day7

import readInput
import readTestInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.first().split(",").map(String::toInt)
        val min = numbers.minOf { it }
        val max = numbers.maxOf { it }
        var bestMinFuel = Int.MAX_VALUE
        (min..max).forEach outer@{ position ->
            var minFuel = 0
            numbers.forEach {
                minFuel += abs(it - position)
                if (minFuel > bestMinFuel) return@outer
            }
            bestMinFuel = minFuel
        }
        return bestMinFuel
    }

    fun part2(input: List<String>): Int {
        val numbers = input.first().split(",").map(String::toInt)
        val min = numbers.minOf { it }
        val max = numbers.maxOf { it }
        var bestMinFuel = Int.MAX_VALUE
        (min..max).forEach outer@{ position ->
            var minFuel = 0
            numbers.forEach {
                minFuel += abs(it - position).lol()
                if (minFuel > bestMinFuel) return@outer
            }
            bestMinFuel = minFuel
        }
        return bestMinFuel
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day7")
    check(part2(testInput) == 168)

    val input = readInput("day7")
    println(part1(input))
    println(part2(input))
}

private fun Int.lol(): Int {
    return (0..this).sum()
}
