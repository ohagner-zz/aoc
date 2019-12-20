package day7.second

class LessThan: Instruction {
    override suspend fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        val parameterModes = getParameterModes(instructions[currentPosition])
        val firstParameter = getParameterValue(instructions[currentPosition + 1], parameterModes[0], instructions)
        val secondParameter = getParameterValue(instructions[currentPosition + 2], parameterModes[1], instructions)
        val destination = instructions[currentPosition + 3]
        if(firstParameter < secondParameter) {
            instructions[destination] = 1
        } else {
            instructions[destination] = 0
        }
        return InstructionResponse(currentPosition + 4, instructions)
    }
}