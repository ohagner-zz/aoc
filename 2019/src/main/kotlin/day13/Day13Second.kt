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
    memory.set(0, 2)
    val input = Channel<Long>(Channel.CONFLATED)
    val output = Channel<Long>()
    val program = Program(memory, input, output)
    val runningProgram = GlobalScope.async { program.run() }

//    var previousGridsize = grid.size() - 1
//    while(grid.size() > previousGridsize) {
//        println("Previous: $previousGridsize, current: ${grid.size()}")
//        previousGridsize = grid.size()
//        val xPosition = withTimeout(1000L) { output.receive() }
//        val yPosition = withTimeout(1000L) { output.receive() }
//        if(xPosition != -1L) {
//            val tile = Tile.fromValue(withTimeout(1000L) { output.receive() })
//            grid.set(Point(xPosition.toInt(), yPosition.toInt()), tile)
//        }
//        println(previousGridsize)
//    }
//    println("Now grid has stopped expanding")
    try {

        var latestTile: Tile = Tile.EMPTY
        var horizontalBallPositions = ArrayDeque<Int>()
        input.send(0)
        while (!runningProgram.isCompleted) {

            val xPosition = withTimeout(1000L) { output.receive() }
            val yPosition = withTimeout(1000L) { output.receive() }
            val zValue = withTimeout(1000L) { output.receive() }

            if (xPosition == -1L && yPosition == 0L) {
                println("Score is $zValue")
            } else {
                val tile = Tile.fromValue(zValue)
                latestTile = tile
                grid.set(Point(xPosition.toInt(), yPosition.toInt()), tile)
            }
            val ballPosition = Optional.ofNullable(grid.entries().find { it.value == Tile.BALL }?.key)
            val paddlePosition = Optional.ofNullable(grid.entries().find { it.value == Tile.PADDLE }?.key)
            println("ballPosition: ${ballPosition.orElse(Point(0,0))} paddlePosition: ${paddlePosition.orElse(Point(0,0))}")
            if(latestTile == Tile.BALL) {
                if(horizontalBallPositions.size == 0 || horizontalBallPositions.first != ballPosition.get().x) {
                    horizontalBallPositions.push(ballPosition.get().x)
                }
                if(horizontalBallPositions.size > 2) {
                    horizontalBallPositions.removeLast()
                }
            }

            if (ballPosition.isPresent && paddlePosition.isPresent) {
                if(horizontalBallPositions.size == 1) {
                    println("Not enough, sending input 0")
                    input.send(0)
                } else if(ballPosition.get().x == paddlePosition.get().x) {
                    println(horizontalBallPositions)
                    println("Sending input ${horizontalBallPositions.first - horizontalBallPositions.last}")
                    input.send((horizontalBallPositions.first - horizontalBallPositions.last).toLong())
                } else {
                    input.send((ballPosition.get().x - paddlePosition.get().x).toLong())
                }
                println(grid)
            }


        }
    } catch (e: TimeoutCancellationException) {
        println("Got timeout " + e.printStackTrace())
    } catch (e: Exception) {
        println("Got other exception: " + e.printStackTrace())
    } finally {
        println(grid)
    }

}

