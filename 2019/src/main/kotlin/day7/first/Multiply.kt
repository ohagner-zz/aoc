package day7.first

class Multiply: Instruction {
    override fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        val parameterModes = getParameterModes(instructions[currentPosition])
        val firstParameter = getParameterValue(instructions[currentPosition + 1], parameterModes[0], instructions)
        val secondParameter = getParameterValue(instructions[currentPosition + 2], parameterModes[1], instructions)
        val destination = instructions[currentPosition + 3]
        instructions[destination] = (firstParameter * secondParameter)
        return InstructionResponse(currentPosition + 4, instructions)
    }
}