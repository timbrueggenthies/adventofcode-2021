package day6

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>) = simulate(extractFishes(input), 80)

    fun part2(input: List<String>) = simulate(extractFishes(input), 256)

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day6")
    check(part2(testInput) == 26984457539L)

    val input = readInput("day6")
    println(part1(input))
    println(part2(input))
}

private fun simulate(fishes: Map<Int, Long>, days: Int): Long {
    var currentEvolution = fishes
    repeat(days) {
        currentEvolution = evolve(currentEvolution)
    }
    return currentEvolution.values.sum()
}

private fun evolve(fishes: Map<Int, Long>): Map<Int, Long> {
    return (0..8).associateWith { day ->
        when (day) {
            6 -> (fishes[7] ?: 0) + (fishes[0] ?: 0)
            8 -> fishes[0] ?: 0
            else -> fishes[day + 1] ?: 0
        }
    }
}

private fun extractFishes(input: List<String>): Map<Int, Long> {
    val numbers = input
        .first()
        .split(",")
        .map(String::toInt)
    return (0..8).associateWith { day ->
        numbers.count { it == day }.toLong()
    }
}
