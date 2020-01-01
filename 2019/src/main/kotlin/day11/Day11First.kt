package day11

import intcode.Memory
import intcode.Program
import kotlinx.coroutines.channels.Channel


fun main() {

    val input = Channel<Long>()
    val output = Channel<Long>()
    val memory = Memory()
    val program = Program(memory, input, output)

}