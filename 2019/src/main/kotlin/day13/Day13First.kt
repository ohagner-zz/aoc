package day13

import common.Grid
import common.InputReader
import common.Point
import intcode.Memory
import intcode.Program
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeout
import java.util.*


suspend fun main() {

    val grid = Grid(Tile.EMPTY)

    val memoryData: List<Long> = InputReader().fromFileCommaSeparated("input/day13/input.txt").map { it.toLong() }
    val memory = Memory()
    memoryData.forEachIndexed { index, value -> memory.set(index.toLong(), value) }
    val input = Channel<Long>(10)
    val output = Channel<Long>()
    val program = Program(memory, input, output)
    val runningProgram = GlobalScope.async { program.run() }


    try {
        while (!runningProgram.isCompleted) {

            val xPosition = withTimeout(1000L) { output.receive() }
            val yPosition = withTimeout(1000L) { output.receive() }
            if(xPosition == -1L) {
                println("Score is ${withTimeout(1000L) { output.receive() }}")
            } else {
                val tile = Tile.fromValue(withTimeout(1000L) { output.receive() })
                grid.set(Point(xPosition.toInt(), yPosition.toInt()), tile)
            }
            println(grid)
        }
    } catch(e: TimeoutCancellationException) {
        println("Got timeout")
    } finally {
        println(grid)
        println(grid.values().stream().filter { it == Tile.BLOCK }.count())
    }

}

