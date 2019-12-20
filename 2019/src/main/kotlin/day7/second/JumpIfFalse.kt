package day7.second

class JumpIfFalse: Instruction {
    override suspend fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        val parameterModes = getParameterModes(instructions[currentPosition])
        val firstParameter = getParameterValue(instructions[currentPosition + 1], parameterModes[0], instructions)
        if(firstParameter == 0) {
            val secondParameter = getParameterValue(instructions[currentPosition + 2], parameterModes[1], instructions)
            return InstructionResponse(secondParameter, instructions)
        } else {
            return InstructionResponse(currentPosition + 3, instructions)
        }

    }
}