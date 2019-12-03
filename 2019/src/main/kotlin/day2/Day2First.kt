package day2

import common.InputReader
import java.util.*

fun main() {

    val startTime: Long = System.currentTimeMillis()
    var instructions: MutableList<Int> = LinkedList()
    instructions.addAll(InputReader().fromFileCommaSeparated("input/day2/first.txt").map { it.toInt()})

    instructions.forEachIndexed { index, value -> println("$index: $value")}
    var currentPosition: Int = 0;

    while(currentPosition + 3 < instructions.size) {
        val operation = instructions.get(currentPosition)

        if(operation == 99) {
            println("Stopping")
            break
        }

        val operandOneIndex = instructions.get(currentPosition + 1)
        val operandOne = instructions.get(operandOneIndex)
        val operandTwoIndex = instructions.get(currentPosition + 2)
        val operandTwo = instructions.get(operandTwoIndex)
        val resultIndex = instructions.get(currentPosition + 3)

        val result = calculateResult(operation, operandOne, operandTwo)

        println("Operation: $operation, setting value from $operandOne and $operandTwo at index $resultIndex")

        instructions.set(resultIndex, result)
        currentPosition += 4
    }
    instructions.forEach { print("$it ") }
    println("Finished in ${System.currentTimeMillis() - startTime} ms")
}

private fun calculateResult(operation: Int, operandOne: Int, operandTwo: Int): Int {
    when (operation) {
        1 -> return operandOne + operandTwo
        2 -> return operandOne * operandTwo
        else -> {
            println("Unknown operation $operation, stopping")
            System.exit(-1)
        }
    }
    return -1
}

