package day7.second

import kotlinx.coroutines.channels.Channel

class OutputInstruction(val outputChannel: Channel<Int>): Instruction {
    override suspend fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse {
        val parameterModes = getParameterModes(instructions[currentPosition])
        val output = getParameterValue(instructions[currentPosition + 1], parameterModes[0], instructions)

        println("OUTCPUT: $output")
        outputChannel.send(output)
        return InstructionResponse(currentPosition + 2, instructions)
    }
}