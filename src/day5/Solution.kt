package day5

import readInput
import readTestInput
import java.lang.Integer.min
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val lines = extractLines(input)
        val points = createStraightPoints(lines)
        return points.flatten()
            .sortedWith(compareBy({ it.first }, { it.second }))
            .groupBy { it }
            .count { it.value.size > 1 }
    }

    fun part2(input: List<String>): Int {
        val lines = extractLines(input)
        val points = createAllPoints(lines)
        return points.flatten()
            .sortedWith(compareBy({ it.first }, { it.second }))
            .groupBy { it }
            .count { it.value.size > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day5")
    check(part2(testInput) == 12)

    val input = readInput("day5")
    println(part1(input))
    println(part2(input))
}

private fun extractLines(input: List<String>) = input
    .map {
        val split = it.split(" -> ")
        val from = split[0]
        val to = split[1]
        val fromX = from.split(",")[0].toInt()
        val fromY = from.split(",")[1].toInt()
        val toX = to.split(",")[0].toInt()
        val toY = to.split(",")[1].toInt()
        val stepX = if (fromX < toX) 1 else -1
        val stepY = if (fromY < toY) 1 else -1
        IntProgression.fromClosedRange(fromX, toX, stepX) to IntProgression.fromClosedRange(fromY, toY, stepY)
    }

private fun createStraightPoints(input: List<Pair<IntProgression, IntProgression>>) = input
    .filter { (xRange, yRange) -> xRange.first == yRange.last || yRange.first == yRange.last }
    .map { (xRange, yRange) ->
            xRange.flatMap { x ->
                yRange.map { y ->
                    x to y
                }
            }
    }

private fun createAllPoints(input: List<Pair<IntProgression, IntProgression>>) = input
    .map { (xRange, yRange) ->
        if (xRange.first != xRange.last && yRange.first != yRange.last) {
            val xIterator = xRange.iterator()
            val yIterator = yRange.iterator()
            buildList {
                while(xIterator.hasNext()) {
                    add(xIterator.nextInt() to yIterator.nextInt())
                }
            }
        } else {
            xRange.flatMap { x ->
                yRange.map { y ->
                    x to y
                }
            }
        }
    }