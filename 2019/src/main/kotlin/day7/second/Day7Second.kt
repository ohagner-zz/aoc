package day7.second

import common.InputReader
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream
import kotlin.streams.toList

class ProgramResult(val value: Int, val instructions: MutableList<Int>, val halted: Boolean = false)

suspend fun main() {
    val start = System.currentTimeMillis()
    var instructions: MutableList<Int> = LinkedList()
    instructions.addAll(InputReader().fromFileCommaSeparated("input/day7/input.txt").map { it.toInt()})
//    instructions.addAll("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5".split(",").map { it.toInt() })
    var maxResult = AmplificationResult("00000", 0)
    val phaseSettings = getAvailablePhaseSettings()
    phaseSettings.forEach {
        println("Running phase $it")
        if(it.equals("98765")) { println("hej")}
        val result = runAmplificationCircuit(it.toList(), instructions)
        println("phase $it result = ${result.output}")
        if (result.output > maxResult.output) {
            println("${result.output} is larger than ${maxResult.output}")
            maxResult = result
        }
    }
    val elapsed = System.currentTimeMillis() - start
    println("Max result is ${maxResult.output} for phase setting ${maxResult.phaseSetting}. Executed in $elapsed ms")
}

fun runAmplificationCircuit(
    phaseSetting: List<Char>,
    instructions: List<Int>
): AmplificationResult {
    var amplificationOutput = 0
    val programInstructions = instructions.toList()
    val channels = mutableListOf<Channel<Int>>()
    repeat(phaseSetting.size) { channels.add(it, Channel<Int>(4)) }
    val resultChannel = Channel<Int>(4)
    println("Before blocking")
    var deferredList: MutableList<Deferred<Unit>> = mutableListOf()
    runBlocking {
        GlobalScope.launch {
            for (x in resultChannel) {
                println("Amplification value: $x, sending to feedback loop")
                amplificationOutput = x
                channels[0].send(x)
            }
        }
        phaseSetting
            .map { it.toString().toInt() }
            .forEachIndexed { index, setting ->

                val inputChannel = channels[index]
                inputChannel.send(setting)
                if (index == 0) {
                    inputChannel.send(0)
                }

                val outputChannel = when (index) {
                    4 -> resultChannel
                    else -> channels[index + 1]
                }
                deferredList.add(GlobalScope.async {
                    runProgram(inputChannel, outputChannel, programInstructions.toMutableList())
                })
            }
        deferredList.awaitAll()

    }
    println("After blocking")
    return AmplificationResult(phaseSetting.joinToString(""), amplificationOutput)
}

suspend fun runProgram(inputChannel: Channel<Int>, outputChannel: Channel<Int>, programInstructions: List<Int>): Unit {
    var instructions = programInstructions.toMutableList()
    var currentPosition = 0
    while (instructions[currentPosition] != 99) {
        val opCode: Int = instructions[currentPosition]
        val instruction = when (getOperation(opCode)) {
            '1' -> Add()
            '2' -> Multiply()
            '3' -> InputInstruction(inputChannel.receive())
            '4' -> OutputInstruction(outputChannel)
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
    outputChannel.cancel()
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


