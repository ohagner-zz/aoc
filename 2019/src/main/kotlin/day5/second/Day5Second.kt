package day5.second

import common.InputReader
import java.lang.RuntimeException
import java.util.*

fun main() {
    val instructions: MutableList<Int> = LinkedList()
    instructions.addAll(InputReader().fromFileCommaSeparated("input/day5/first.txt").map { it.toInt()})
//    instructions.addAll("3,3,1105,-1,9,1101,0,0,12,4,12,99,1".split(",").map { it.toInt() }.toList())

    var currentPosition = 0
    val input = 5
    while(instructions[currentPosition] != 99) {
        val opCode: Int = instructions[currentPosition]
        when(getOperation(opCode)) {
            '1' -> {
                println("Read opcode 1, next is " + instructions[currentPosition + 1] + ", " + instructions[currentPosition + 2] + ", " + instructions[currentPosition + 3])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val firstParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                val secondParameter = Parameter(
                    instructions.get(currentPosition + 2),
                    fromValue(paddedOpcode.get(1))
                )
                val destinationParameter = Parameter(
                    instructions[currentPosition + 3],
                    ParameterMode.IMMEDIATE
                )
                val sum = getParameterValue(
                    firstParameter,
                    instructions
                ) + getParameterValue(secondParameter, instructions)

                val destination = getParameterValue(destinationParameter, instructions)
                instructions[destination] = sum
                currentPosition += 4
            }
            '2' -> {
                println("Read opcode 2, next is " + instructions[currentPosition + 1] + ", " + instructions[currentPosition + 2] + ", " + instructions[currentPosition + 3])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val firstParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                val secondParameter = Parameter(
                    instructions.get(currentPosition + 2),
                    fromValue(paddedOpcode.get(1))
                )
                val destinationParameter = Parameter(
                    instructions[currentPosition + 3],
                    ParameterMode.IMMEDIATE
                )
                instructions[getParameterValue(destinationParameter, instructions)] = getParameterValue(
                    firstParameter,
                    instructions
                ) * getParameterValue(secondParameter, instructions)
                currentPosition += 4
            }
            '3' -> {
                println("Read opcode 3, next is " + instructions[currentPosition + 1])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val destination = instructions.get(currentPosition + 1)
                instructions[destination] = input

                currentPosition += 2
            }
            '4' -> {
                println("Read opcode 4, next is " + instructions[currentPosition + 1])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val destinationParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                println("OUTPUT: " + getParameterValue(
                    destinationParameter,
                    instructions
                )
                )
                currentPosition += 2
            }
            '5' -> {
                println("Read opcode 5, next is " + instructions[currentPosition + 1])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val firstParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                val firstParameterValue = getParameterValue(firstParameter, instructions)
                if(firstParameterValue != 0) {
                    val secondParameter = Parameter(
                        instructions.get(currentPosition + 2),
                        fromValue(paddedOpcode.get(1))
                    )
                    val secondParameterValue =
                        getParameterValue(secondParameter, instructions)
                    println("Moving instruction pointer to $secondParameterValue")
                    currentPosition = secondParameterValue
                } else {
                    currentPosition += 3
                }
            }
            '6' -> {
                println("Read opcode 6, next is " + instructions[currentPosition + 1])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val firstParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                val firstParameterValue = getParameterValue(firstParameter, instructions)
                if(firstParameterValue == 0) {
                    val secondParameter = Parameter(
                        instructions.get(currentPosition + 2),
                        fromValue(paddedOpcode.get(1))
                    )
                    val secondParameterValue =
                        getParameterValue(secondParameter, instructions)
                    println("Moving instruction pointer to $secondParameterValue")
                    currentPosition = secondParameterValue
                } else {
                    currentPosition += 3
                }
            }
            '7' -> {
                println("Read opcode 7, next is " + instructions[currentPosition + 1] + ", " + instructions[currentPosition + 2] + ", " + instructions[currentPosition + 3])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val firstParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                val firstParameterValue = getParameterValue(firstParameter, instructions)
                val secondParameter = Parameter(
                    instructions.get(currentPosition + 2),
                    fromValue(paddedOpcode.get(1))
                )
                val secondParameterValue =
                    getParameterValue(secondParameter, instructions)
                val destinationParameter = Parameter(
                    instructions[currentPosition + 3],
                    ParameterMode.IMMEDIATE
                )
                val destination = getParameterValue(destinationParameter, instructions)
                if(firstParameterValue < secondParameterValue) {
                    instructions[destination] = 1
                } else {
                    instructions[destination] = 0
                }
                currentPosition += 4
            }
            '8' -> {
                println("Read opcode 8, next is " + instructions[currentPosition + 1] + ", " + instructions[currentPosition + 2] + ", " + instructions[currentPosition + 3])
                val paddedOpcode: String = opCode.toString().padStart(5, '0')
                val firstParameter = Parameter(
                    instructions.get(currentPosition + 1),
                    fromValue(paddedOpcode.get(2))
                )
                val firstParameterValue = getParameterValue(firstParameter, instructions)
                val secondParameter = Parameter(
                    instructions.get(currentPosition + 2),
                    fromValue(paddedOpcode.get(1))
                )
                val secondParameterValue =
                    getParameterValue(secondParameter, instructions)
                val destinationParameter = Parameter(
                    instructions[currentPosition + 3],
                    ParameterMode.IMMEDIATE
                )
                val destination = getParameterValue(destinationParameter, instructions)
                if(firstParameterValue == secondParameterValue) {
                    instructions[destination] = 1
                } else {
                    instructions[destination] = 0
                }
                currentPosition += 4
            }
            else -> throw RuntimeException("Bad operation")
        }
    }
}

private fun getOperation(value: Int): Char {
    println(value.toString().last())
    return value.toString().last()
}

private class Parameter(val value: Int, val parameterMode: ParameterMode) {
    override fun toString(): String {
        return "Parameter(value=$value, parameterMode=$parameterMode)"
    }
}

private enum class ParameterMode {
    POSITION, IMMEDIATE;
}

private fun getParameterValue(parameter: Parameter, instructions: List<Int>): Int {
    return when(parameter.parameterMode) {
        ParameterMode.POSITION -> instructions.get(parameter.value)
        ParameterMode.IMMEDIATE -> parameter.value
    }
}

private fun fromValue(i: Char): ParameterMode {
    return when (i) {
        '0' -> ParameterMode.POSITION
        '1' -> ParameterMode.IMMEDIATE
        else -> throw RuntimeException("Invalid parameter mode value")
    }
}