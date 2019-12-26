package day9.intcode

import kotlinx.coroutines.channels.Channel

class InputInstruction(val inputChannel: Channel<Long>): Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val parameterModes = getParameterModes(memory.get(positions.currentPosition))
        val destination = memory.getParameterValue(positions.currentPosition + 1, positions.relativeBase, parameterModes[0])
        val inputData = inputChannel.receive()
        println("Opcode: ${memory.get(positions.currentPosition)}, parameter: ${memory.get(positions.currentPosition + 1)}. Got input $inputData, putting in destination $destination, relativeBase = ${positions.relativeBase}, parameterMode = ${parameterModes[0]}")
        memory.set(destination, inputData)
        return InstructionResponse(ProgramPositions(positions.currentPosition + 2, positions.relativeBase), memory)
    }
}