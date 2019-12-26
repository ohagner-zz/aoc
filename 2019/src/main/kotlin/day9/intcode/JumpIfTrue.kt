package day9.intcode

class JumpIfTrue: Instruction {

    override suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse {
        val currentPosition = positions.currentPosition
        val relativeBase = positions.relativeBase
        val parameterModes = getParameterModes(memory.get(currentPosition))
        val firstParameter = getParameterValue(memory.get(currentPosition + 1), relativeBase, parameterModes[0], memory)
        if (firstParameter != 0L) {
            val jumpDestination = getParameterValue(memory.get(currentPosition + 2), relativeBase, parameterModes[1], memory)
            return InstructionResponse(ProgramPositions(jumpDestination, relativeBase), memory)
        } else {
            return InstructionResponse(ProgramPositions(currentPosition + 3, relativeBase), memory)
        }

    }
}