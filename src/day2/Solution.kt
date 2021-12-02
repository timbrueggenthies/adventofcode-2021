package day2

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        input.map {
            val split = it.split(" ")
            val command = split[0]
            val amount = split[1].toInt()
            command to amount
        }.forEach { (command, amount) ->
            when (command) {
                "forward" -> horizontal += amount
                "down" -> depth += amount
                "up" -> depth -= amount
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        input.map {
            val split = it.split(" ")
            val command = split[0]
            val amount = split[1].toInt()
            command to amount
        }.forEach { (command, amount) ->
            when (command) {
                "forward" -> {
                    horizontal += amount
                    depth += aim * amount
                }
                "down" -> aim += amount
                "up" -> aim -= amount
            }
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day2")
    check(part2(testInput) == 900)

    val input = readInput("day2")
    println(part1(input))
    println(part2(input))
}
