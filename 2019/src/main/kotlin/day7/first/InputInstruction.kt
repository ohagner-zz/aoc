package day7.first

class InputInstruction(val input: Int): Instruction {
    override fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        println("Reading input $input")
        val destination = instructions.get(currentPosition + 1)
        instructions[destination] = input
        return InstructionResponse(currentPosition + 2, instructions)
    }
}