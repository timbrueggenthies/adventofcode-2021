package day10

import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        val chunks = extractChunks(input)
        return chunks.sumOf { chunk -> chunk.firstInvalidCharacter()?.chunkType?.errorPoints ?: 0 }
    }

    fun part2(input: List<String>): Long {
        val chunks = extractChunks(input)
        return chunks
            .filter { chunk -> chunk.firstInvalidCharacter() == null }
            .map { chunk ->
                chunk
                    .unclosedCharacters()
                    .reversed()
                    .fold(0L) { sum, char -> sum * 5 + char.chunkType.autocompletePoints }
            }
            .sorted()
            .middle()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day10")
    check(part2(testInput) == 288957L)

    val input = readInput("day10")
    println(part1(input))
    println(part2(input))
}

private fun extractChunks(input: List<String>) = input.map { row -> Chunk(row.map { it.toChunkChar() }) }

private fun Chunk.firstInvalidCharacter(): ChunkChar? {
    val stack = mutableListOf<ChunkChar>()
    chunkChars.forEach {
        if (it.open) {
            stack.push(it)
        } else {
            val lastItem = stack.pop()
            if (lastItem?.chunkType != it.chunkType || !lastItem.open) {
                return it
            }
        }
    }
    return null
}

private fun Chunk.unclosedCharacters(): List<ChunkChar> {
    val stack = mutableListOf<ChunkChar>()
    chunkChars.forEach {
        if (it.open) {
            stack.push(it)
        } else {
            stack.pop()
        }
    }
    return stack
}

private val ChunkType.errorPoints: Int get() = when(this) {
    ChunkType.Bracket -> 3
    ChunkType.SquareBracket -> 57
    ChunkType.CurlyBrackets -> 1197
    ChunkType.Comparator -> 25137
}

private val ChunkType.autocompletePoints: Int get() = when(this) {
    ChunkType.Bracket -> 1
    ChunkType.SquareBracket -> 2
    ChunkType.CurlyBrackets -> 3
    ChunkType.Comparator -> 4
}

private fun Char.toChunkChar() = when(this) {
    '(' -> ChunkChar(ChunkType.Bracket, open = true)
    ')' -> ChunkChar(ChunkType.Bracket, open = false)
    '[' -> ChunkChar(ChunkType.SquareBracket, open = true)
    ']' -> ChunkChar(ChunkType.SquareBracket, open = false)
    '{' -> ChunkChar(ChunkType.CurlyBrackets, open = true)
    '}' -> ChunkChar(ChunkType.CurlyBrackets, open = false)
    '<' -> ChunkChar(ChunkType.Comparator, open = true)
    '>' -> ChunkChar(ChunkType.Comparator, open = false)
    else -> error("Unknown Char")
}

data class Chunk(
    val chunkChars: List<ChunkChar>
)

data class ChunkChar(
    val chunkType: ChunkType,
    val open: Boolean
)

enum class ChunkType {
    Bracket, SquareBracket, CurlyBrackets, Comparator
}

fun <T> MutableList<T>.push(item: T) = add(item)

fun <T> MutableList<T>.pop(): T? = if (isNotEmpty()) removeAt(lastIndex) else null

fun <T> List<T>.middle(): T = this[size / 2]