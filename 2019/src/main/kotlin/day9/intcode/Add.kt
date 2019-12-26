package day9.intcode

class Add: Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val currentPosition = positions.currentPosition
        val relativeBase = positions.relativeBase
        val parameterModes = getParameterModes(memory.get(currentPosition))
        val firstParameter = getParameterValue(memory.get(currentPosition + 1), relativeBase, parameterModes[0], memory)
        val secondParameter = getParameterValue(memory.get(currentPosition + 2), relativeBase, parameterModes[1], memory)
        val destination = memory.get(currentPosition + 3)
        memory.set(destination, firstParameter + secondParameter)
        return InstructionResponse(ProgramPositions(currentPosition + 4, relativeBase), memory)
    }
}