package day7.second

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream
import kotlin.streams.toList

class ProgramResult(val value: Int, val instructions: MutableList<Int>, val halted: Boolean = false)

suspend fun main() {
    val start = System.currentTimeMillis()
    var instructions: MutableList<Int> = LinkedList()
    //instructions.addAll(InputReader().fromFileCommaSeparated("input/day7/input.txt").map { it.toInt()})
    instructions.addAll("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5".split(",").map { it.toInt() })
    var maxResult = AmplificationResult("00000", 0)
    val phaseSettings = listOf("98765")//getAvailablePhaseSettings()
    var feedback = 0;
    phaseSettings.forEach {
        val result = runAmplificationCircuit(it.toList(), instructions, feedback)
        feedback = result.output
        if (result.output > maxResult.output) {
            maxResult = result
        }
    }
    val elapsed = System.currentTimeMillis() - start
    println("Max result is ${maxResult.output} for phase setting ${maxResult.phaseSetting}. Executed in $elapsed ms")
}

suspend fun runAmplificationCircuit(phaseSetting: List<Char>, instructions: List<Int>, feedback: Int): AmplificationResult {
    var amplificationOutput = ProgramResult(0, emptyList<Int>().toMutableList())
    var amplificationInput = ProgramResult(feedback, instructions.toMutableList())
    val programInstructions = instructions.toList()
    val channels = mutableListOf<Channel<Int>>()
    phaseSetting
        .map { it.toString().toInt() }
        .forEachIndexed { setting, index ->
            val inputChannel = Channel<Int>()
            channels.set(index, inputChannel)
            inputChannel.send(setting)
            inputChannel.send(0)
            GlobalScope.launch {
                amplificationOutput = runProgram(
                    channels[index], amplificationInput.value),
                    programInstructions.toMutableList()
                )
            }
            amplificationInput = amplificationOutput
        }
    return AmplificationResult(phaseSetting.joinToString(""), amplificationOutput.value)
}

suspend fun runProgram(input: Channel<Int>, output: Channel<Int> programInstructions: List<Int>): ProgramResult {
    var output = -1;
    var instructions = programInstructions.toMutableList()
    var currentPosition = 0
    while (instructions[currentPosition] != 99) {
        val opCode: Int = instructions[currentPosition]
        val instruction = when (getOperation(opCode)) {
            '1' -> Add()
            '2' -> Multiply()
            '3' -> InputInstruction(input.receive())
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
    }

    return ProgramResult(output, instructions, instructions[currentPosition] == 99)
}

private fun getOperation(value: Int): Char {
    return value.toString().last()
}

private fun getAvailablePhaseSettings(): List<String> {
    val counter = AtomicInteger()
    return Stream.generate(counter::getAndIncrement)
        .skip(56788)
        .limit(50000)
        .map { it.toString().toList() }
        .filter { it.containsAll(listOf('5', '6', '7', '8', '9')) }
        .map { it.joinToString("") }
        .toList()
}


