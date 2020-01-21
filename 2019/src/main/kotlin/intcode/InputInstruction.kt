package intcode

import kotlinx.coroutines.channels.Channel

class InputInstruction(val inputChannel: Channel<Long>): Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val parameterModes = getParameterModes(memory.get(positions.currentPosition))
        val destination = memory.getOutputDestination(positions.currentPosition + 1, positions.relativeBase, parameterModes[0])
        println("Waiting for input")
        val inputData = inputChannel.receive()
//        println("Got input $inputData, writing to destination $destination, relativeBase = ${positions.relativeBase}, parameterMode = ${parameterModes[0]}")
        memory.set(destination, inputData)
        return InstructionResponse(ProgramPositions(positions.currentPosition + 2, positions.relativeBase), memory)
    }
}