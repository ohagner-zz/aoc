package intcode

class JumpIfFalse: Instruction {
    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val currentPosition = positions.currentPosition
        val relativeBase = positions.relativeBase
        val parameterModes = getParameterModes(memory.get(currentPosition))
        val firstParameterValue = getParameterValue(memory.get(currentPosition + 1), relativeBase, parameterModes[0], memory)
        return if(firstParameterValue == 0L) {
            val secondParameterValue = getParameterValue(memory.get(currentPosition + 2), relativeBase, parameterModes[1], memory)
            println("JumpIfFalse: Jumping to $secondParameterValue")
            InstructionResponse(ProgramPositions(secondParameterValue, relativeBase), memory)
        } else {
            println("JumpIfFalse: Jumping to ${currentPosition + 3}")
            InstructionResponse(ProgramPositions(currentPosition + 3, relativeBase), memory)
        }

    }
}