package day7.first

import common.InputReader
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream
import kotlin.streams.toList

fun main() {
    val start = System.currentTimeMillis()
    var instructions: MutableList<Int> = LinkedList()
    instructions.addAll(InputReader().fromFileCommaSeparated("input/day7/input.txt").map { it.toInt()})
    val phaseSettings = getAvailablePhaseSettings()
    var maxResult = AmplificationResult("00000", 0)
    phaseSettings.forEach {
        val result = runAmplificationCircuit(it.toList(), instructions)
        if(result.output > maxResult.output) {
            maxResult = result
        }
    }
    val elapsed = System.currentTimeMillis() - start
    println("Max result is ${maxResult.output} for phase setting ${maxResult.phaseSetting}. Executed in $elapsed ms")
}

fun runAmplificationCircuit(phaseSetting: List<Char>, instructions: List<Int>): AmplificationResult {
    var amplificationOutput = -1;
    var amplificationInput = 0;
    phaseSetting
        .map { it.toString().toInt() }
        .forEach { setting ->
            amplificationOutput = runProgram(
                mutableListOf(setting, amplificationInput),
                instructions.toMutableList()
            )
            amplificationInput = amplificationOutput
        }
    return AmplificationResult(phaseSetting.joinToString(""), amplificationOutput)
}

fun runProgram(input: MutableList<Int>, programInstructions: List<Int>): Int {
    var output = -1;
    var instructions = programInstructions.toMutableList()
    var currentPosition = 0
    while(instructions[currentPosition] != 99) {
        val opCode: Int = instructions[currentPosition]
        val instruction =  when(getOperation(opCode)) {
            '1' -> Add()
            '2' -> Multiply()
            '3' -> InputInstruction(input.removeAt(0))
            '4' -> OutputInstruction()
            '5' -> JumpIfTrue()
            '6' -> JumpIfFalse()
            '7' -> LessThan()
            '8' -> EqualsInstruction()
            else -> throw RuntimeException("Bad operation")
        }
        val response = instruction.execute(currentPosition, instructions.toMutableList())
        currentPosition = response.nextPosition
        instructions = response.updatedInstructions
        if(response.output != null) {
            output = response.output
        }
    }
    return output
}

private fun getOperation(value: Int): Char {
    return value.toString().last()
}

private fun getAvailablePhaseSettings(): List<String> {
    val counter = AtomicInteger()
    return Stream.generate(counter::getAndIncrement)
        .skip(1233)
        .limit(50000)
        .map { it.toString().padStart(5, '0') }
        .map { it.toList() }
        .filter { it.containsAll(listOf('0','1', '2', '3', '4')) }
        .map { it.joinToString("") }
        .toList()
}


