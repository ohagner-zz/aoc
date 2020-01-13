package day11

import common.Direction
import common.Direction.*
import common.Grid
import common.InputReader
import common.Point
import day11.Paint.*
import intcode.Memory
import intcode.Program
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel


suspend fun main() {

    val input = Channel<Long>()
    val output = Channel<Long>()
    val memoryData: List<Long> = InputReader().fromFileCommaSeparated("input/day11/input.txt").map { it.toLong() }
    val memory = Memory()
    memoryData.forEachIndexed { index, value -> memory.set(index.toLong(), value) }
    val program = Program(memory, input, output)

    val runningProgram = GlobalScope.async { program.run() }

    val grid = Grid(BLACK)
    var gridPosition = Point(2, 2)
    var gridDirection = UP
    try {
        while (!runningProgram.isCompleted) {
            withTimeout(1000L) { input.send(grid.get(gridPosition).value) }
            val colorToPaint = colorOf(withTimeout(1000L) { output.receive() })
            val directionToTurn = directionOf(withTimeout(1000L) { output.receive() })
            println("Painting $colorToPaint, then turning $directionToTurn")
            grid.set(gridPosition, colorToPaint)
            gridDirection = gridDirection.turn(directionToTurn)
            gridPosition = gridPosition.move(gridDirection)
            println("Moved $gridDirection to position $gridPosition")
        }
    } catch(e: TimeoutCancellationException) {
        println("Got timeout")
    } finally {
        println(grid)
    }

    val paintedAreas = grid.values()
        .count()
    println("Had to paint at least $paintedAreas areas")

}

fun colorOf(value: Long): Paint {
    println("Color: $value")
    return when (value) {
        0L -> BLACK
        1L -> WHITE
        else -> throw RuntimeException("Unknown paint color: $value")
    }
}

fun directionOf(value: Long): Direction {
    println("Direction: $value")
    return when (value) {
        0L -> LEFT
        1L -> RIGHT
        else -> throw RuntimeException("Unknown direction: $value")
    }
}