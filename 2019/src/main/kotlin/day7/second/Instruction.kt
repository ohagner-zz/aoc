package day7.second

interface Instruction {
    suspend fun execute(currentPosition: Int, instructions: MutableList<Int>): InstructionResponse

    fun getParameterValue(instructionValue: Int, parameterMode: ParameterMode, instructions: List<Int>): Int {
        return when(parameterMode) {
            ParameterMode.POSITION -> instructions[instructionValue]
            ParameterMode.IMMEDIATE -> instructionValue
        }
    }

    fun getParameterModes(opCode: Int): List<ParameterMode> {
        return opCode.toString()
            .padStart(5,'0')
            .reversed()
            .drop(2)
            .toList()
            .map { ParameterMode.fromValue(it) }
    }
}