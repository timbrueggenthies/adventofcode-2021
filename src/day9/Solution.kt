package day9

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        val heightmap = extractHeightmap(input)
        val lowPoints = findLowPoints(heightmap)
        return lowPoints.sumOf { heightmap[it] + 1 }
    }

    fun part2(input: List<String>): Int {
        val heightmap = extractHeightmap(input)
        val lowPoints = findLowPoints(heightmap)
        val basinSizes = findBasinSizes(heightmap, lowPoints)
        return basinSizes.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day9")
    check(part2(testInput) == 1134)

    val input = readInput("day9")
    println(part1(input))
    println(part2(input))
}

private operator fun List<List<Int>>.get(pos: Pair<Int, Int>): Int {
    return get(pos.first, pos.second)
}

private operator fun List<List<Int>>.get(i: Int, j: Int): Int {
    val list = try {
        get(i)
    } catch (e: Throwable) {
        return Int.MAX_VALUE
    }
    return try {
        list[j]
    } catch (e: Throwable) {
        Int.MAX_VALUE
    }
}

private fun findBasinSizes(heightmap: List<List<Int>>, lowPoints: List<Pair<Int, Int>>): List<Int> {
    return lowPoints.map { startingPoint ->
        val checkedPoints = mutableSetOf<Pair<Int, Int>>()
        val pointsToCheck = mutableSetOf(startingPoint)
        var size = 0
        while (pointsToCheck.isNotEmpty()) {
            size++
            val current = pointsToCheck.first()
            pointsToCheck.remove(current)
            val adjacents = findNonNineAdjacents(heightmap, current).filter { it !in checkedPoints }
            pointsToCheck.addAll(adjacents)
            checkedPoints.add(current)
        }
        size
    }
}

private fun findAdjacentsWhere(
    heightmap: List<List<Int>>,
    position: Pair<Int, Int>,
    predicate: (Pair<Int, Int>) -> Boolean
): List<Pair<Int, Int>> {
    val (i, j) = position
    val up = i - 1 to j
    val down = i + 1 to j
    val left = i to j - 1
    val right = i to j + 1
    return listOf(up, down, left, right)
        .filter { (i, j) -> i in heightmap.indices && j in heightmap.first().indices }
        .filter { predicate(it) }
}

private fun findLowerAdjacents(heightmap: List<List<Int>>, position: Pair<Int, Int>): List<Pair<Int, Int>> {
    return findAdjacentsWhere(heightmap, position) { heightmap[it] < heightmap[position] }
}

private fun findNonNineAdjacents(heightmap: List<List<Int>>, position: Pair<Int, Int>): List<Pair<Int, Int>> {
    return findAdjacentsWhere(heightmap, position) { heightmap[it] != 9 }
}

private fun findLowPoints(heightmap: List<List<Int>>): List<Pair<Int, Int>> {
    val lowPoints = mutableListOf<Pair<Int, Int>>()
    heightmap.indices.forEach { i ->
        heightmap.first().indices.forEach inner@{ j ->
            if (findLowerAdjacents(heightmap, i to j).isEmpty()) {
                lowPoints += i to j
            }
        }
    }
    return lowPoints
}

private fun extractHeightmap(input: List<String>) = input.map { it.map(Char::digitToInt) }
