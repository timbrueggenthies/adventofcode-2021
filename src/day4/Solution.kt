package day4

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        val (numbers, boards) = extractBingo(input)
        val mutableBoards = createMutableBoards(boards)

        numbers.forEach { number ->
            mutableBoards.forEach { (rowOrder, colOrder) ->
                rowOrder.forEach { row -> row.remove(number) }
                colOrder.forEach { col -> col.remove(number) }
            }
            val finishedBoard = mutableBoards.firstOrNull { it.isFinished() }
            if (finishedBoard != null) {
                return finishedBoard.first.flatten().sum() * number
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val (numbers, boards) = extractBingo(input)
        val mutableBoards = createMutableBoards(boards).toMutableList()

        numbers.forEach { number ->
            mutableBoards.forEach { (rowOrder, colOrder) ->
                rowOrder.forEach { row -> row.remove(number) }
                colOrder.forEach { col -> col.remove(number) }
            }
            mutableBoards.singleOrNull()?.let { lastBoard ->
                if (lastBoard.isFinished()) return lastBoard.first.flatten().sum() * number
            }
            mutableBoards.removeAll { it.isFinished() }
        }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day4")
    check(part2(testInput) == 1924)

    val input = readInput("day4")
    println(part1(input))
    println(part2(input))
}

private fun Pair<List<List<Int>>, List<List<Int>>>.isFinished(): Boolean {
    val (rowOrder, colOrder) = this
    return rowOrder.any { it.isEmpty() } || colOrder.any { it.isEmpty() }
}

private fun createMutableBoards(boards: List<List<List<Int>>>) = boards.map { board ->
    val rowOrder = board.map { it.toMutableList() }.toMutableList()
    val colOrder = board.indices.map { col ->
        board.indices.map { row ->
            rowOrder[row][col]
        }.toMutableList()
    }.toMutableList()
    rowOrder to colOrder
}

private fun extractBingo(input: List<String>): Pair<List<Int>, List<List<List<Int>>>> {
    val numbers = input.first().split(",").map(String::toInt)
    val boardSize = input.drop(2).takeWhile { it.isNotBlank() }.size
    val boards = input
        .drop(2)
        .filter { it.isNotBlank() }
        .chunked(boardSize)
        .map { board ->
            board.map { it.split(" ").filter { it.isNotBlank() }.map(String::toInt) }
        }

    return numbers to boards
}