package day7.second

class InputInstruction(val input: Int): Instruction {
    override suspend fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        val destination = instructions.get(currentPosition + 1)
        instructions[destination] = input
        return InstructionResponse(currentPosition + 2, instructions)
    }
}