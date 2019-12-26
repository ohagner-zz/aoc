package day9.intcode

class AdjustRelativeBase: Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val parameterModes = getParameterModes(memory.get(positions.currentPosition))
        val firstParameter = getParameterValue(memory.get(positions.currentPosition + 1), positions.relativeBase, parameterModes[0], memory)
        return InstructionResponse(ProgramPositions(positions.currentPosition + 2, positions.relativeBase + firstParameter), memory)
    }
}
