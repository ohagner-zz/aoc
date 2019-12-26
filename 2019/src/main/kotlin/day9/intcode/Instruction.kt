package day9.intcode

interface Instruction {
    suspend fun execute(positions: ProgramPositions, memory: Memory): InstructionResponse

    fun getParameterValue(parameterValue: Long, relativeBase: Long, parameterMode: ParameterMode, memory: Memory): Long {
//        println("Retrieving data, parameterValue $parameterValue, parameterMode: $parameterMode")
        return when(parameterMode) {
            ParameterMode.POSITION -> memory.get(parameterValue)
            ParameterMode.IMMEDIATE -> parameterValue
            ParameterMode.RELATIVE -> memory.get(parameterValue + relativeBase)
        }
    }

    fun getParameterModes(opCode: Long): List<ParameterMode> {
        return opCode.toString()
            .padStart(5,'0')
            .reversed()
            .drop(2)
            .toList()
            .map { ParameterMode.fromValue(it) }
    }
}