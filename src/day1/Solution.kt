package day1

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2, 1)
            .fold(0) { acc, (first, second) -> if (second > first) acc + 1 else acc }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toInt() }
            .windowed(3, 1)
            .map { it.sum() }
            .windowed(2, 1)
            .fold(0) { acc, (first, second) -> if (second > first) acc + 1 else acc }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day1")
    check(part2(testInput) == 5)

    val input = readInput("day1")
    println(part1(input))
    println(part2(input))
}
