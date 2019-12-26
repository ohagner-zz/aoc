package day9.intcode

class EqualsInstruction: Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val currentPosition = positions.currentPosition
        val relativeBase = positions.relativeBase
        val parameterModes = getParameterModes(memory.get(currentPosition))
        val firstParameter = getParameterValue(memory.get(currentPosition + 1), relativeBase, parameterModes[0], memory)
        val secondParameter = getParameterValue(memory.get(currentPosition + 2), relativeBase, parameterModes[1], memory)
        val destination = memory.get(currentPosition + 3)
        if(firstParameter == secondParameter) {
            memory.set(destination, 1)
        } else {
            memory.set(destination, 0)
        }
        return InstructionResponse(ProgramPositions(currentPosition + 4, relativeBase), memory)
    }
}