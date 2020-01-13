package intcode

class AdjustRelativeBase: Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val parameterModes = getParameterModes(memory.get(positions.currentPosition))
        val relativeBaseOffset = getParameterValue(memory.get(positions.currentPosition + 1), positions.relativeBase, parameterModes[0], memory)
//        println("Adjusting relative base with $relativeBaseOffset")
        return InstructionResponse(ProgramPositions(positions.currentPosition + 2, positions.relativeBase + relativeBaseOffset), memory)
    }
}
