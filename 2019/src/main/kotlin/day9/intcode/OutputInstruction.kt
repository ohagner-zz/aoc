package day9.intcode

import kotlinx.coroutines.channels.Channel

class OutputInstruction(val outputChannel: Channel<Long>): Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val currentPosition = positions.currentPosition
        val relativeBase = positions.relativeBase

        val parameterModes = getParameterModes(memory.get(currentPosition))
        val output = getParameterValue(memory.get(currentPosition + 1), relativeBase, parameterModes[0], memory)
        println("Output: Writing value $output")
        outputChannel.send(output)
//        println("OUTPUT: $output")
        return InstructionResponse(ProgramPositions(currentPosition + 2, relativeBase), memory)
    }
}