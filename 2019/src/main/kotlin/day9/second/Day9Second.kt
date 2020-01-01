package day9.second

import common.InputReader
import day9.intcode.Memory
import day9.intcode.Program
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


suspend fun main() {
//    val input: List<Long> = InputReader().fromCommaSeparated("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99").map {  it.toLong() }
//    val input: List<Long> = InputReader().fromCommaSeparated("1102,34915192,34915192,7,4,7,99,0").map {  it.toLong() }
//    val input: List<Long> = InputReader().fromCommaSeparated("104,1125899906842624,99").map {  it.toLong() }
    val input: List<Long> = InputReader().fromFileCommaSeparated("input/day9/first.txt").map {  it.toLong() }
    val memory = Memory()
    input.forEachIndexed{ index, value -> memory.set(index.toLong(), value) }
    val inputChannel = Channel<Long>()
    val outputChannel = Channel<Long>()

    GlobalScope.launch {
        inputChannel.send(2L)
    }
    GlobalScope.launch {
        Program(memory, inputChannel, outputChannel).run()
    }

    for(value in outputChannel) {
        print("$value ")
    }
}