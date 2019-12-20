package day7.first

class OutputInstruction(): Instruction {
    override fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        val parameterModes = getParameterModes(instructions[currentPosition])
        val output = getParameterValue(instructions[currentPosition + 1], parameterModes[0], instructions)

//        println("OUTPUT: $output")
        return InstructionResponse(currentPosition + 2, instructions, output)
    }
}