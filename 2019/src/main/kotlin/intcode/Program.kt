package intcode

import kotlinx.coroutines.channels.Channel


class Program(var memory: Memory, val input: Channel<Long>, val output: Channel<Long>) {

    private var currentPosition: Long = 0
    private var relativeBase: Long = 0

    var isRunning = false

    suspend fun run(): Unit {
        isRunning = true
        while(memory.get(currentPosition) != 99L) {
            val opCode: Long = memory.get(currentPosition)
            val instruction =  when(getOperation(opCode)) {
                '1' -> Add()
                '2' -> Multiply()
                '3' -> InputInstruction(input)
                '4' -> OutputInstruction(output)
                '5' -> JumpIfTrue()
                '6' -> JumpIfFalse()
                '7' -> LessThan()
                '8' -> EqualsInstruction()
                '9' -> AdjustRelativeBase()
                else -> throw RuntimeException("Bad operation ${getOperation(opCode)}")
            }
            val response = instruction.execute(ProgramPositions(currentPosition, relativeBase), memory)
            currentPosition = response.positions.currentPosition
            relativeBase = response.positions.relativeBase
        }

        isRunning = false
        input.close()
        output.close()
        println("DONE!!")
    }

    private fun getOperation(value: Long): Char {
        return value.toString().last()
    }
}