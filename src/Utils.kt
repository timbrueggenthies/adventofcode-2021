import java.io.File

fun readInput(name: String) = File(inputDir(name), "Input.txt").readLines()

fun readTestInput(name: String) = File(inputDir(name), "TestInput.txt").readLines()

private fun inputDir(name: String): String = File("src", name).path
