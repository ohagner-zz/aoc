package intcode

class Multiply: Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val currentPosition = positions.currentPosition
        val relativeBase = positions.relativeBase

        val parameterModes = getParameterModes(memory.get(currentPosition))
        val firstParameter = getParameterValue(memory.get(currentPosition + 1), relativeBase, parameterModes[0], memory)
        val secondParameter = getParameterValue(memory.get(currentPosition + 2), relativeBase, parameterModes[1], memory)
//        println("Retrieving parameter value from ${currentPosition+3}, parameterMode: ${parameterModes[2]}")
        val destination = memory.getOutputDestination(currentPosition + 3, relativeBase, parameterModes[2])
//        println("Multiplying $firstParameter with $secondParameter, writing result to $destination")
        memory.set(destination, firstParameter * secondParameter)
//        println("Value in $destination is ${memory.get(destination)}")
        return InstructionResponse(ProgramPositions(currentPosition + 4, relativeBase), memory)
    }
}