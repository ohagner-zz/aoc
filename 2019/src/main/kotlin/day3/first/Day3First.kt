package day3.first

import common.InputReader
import java.lang.RuntimeException
import kotlin.math.abs

fun main() {
    var grid: MutableMap<Coordinate, String> = HashMap()

    val firstInput: List<String> = InputReader().fromFileCommaSeparated("input/day3/firstInput1.txt")
    var current = Coordinate(0, 0)
    firstInput.forEach{ direction ->
        current.path(direction).forEach{ coordinate ->
            grid.put(coordinate, "a")
        }
        //println("Moving $direction. From $current to ${current.destination(direction)}")
        current = current.destination(direction)
    }
    val secondInput: List<String> = InputReader().fromFileCommaSeparated("input/day3/firstInput2.txt")
    current = Coordinate(0, 0)
    secondInput.forEach{ direction ->
        current.path(direction).forEach{ coordinate ->
            grid.merge(coordinate, "b", { oldValue, defaultValue -> oldValue + "b" })
        }
        //println("Moving $direction. From $current to ${current.destination(direction)}")
        current = current.destination(direction)
    }
    val closest = grid
        .filter { entry -> entry.value.contains("ab") }
        .minBy { entry -> entry.key.manhattanDistance() }
        ?.key
    println(closest?.manhattanDistance())
}

class Coordinate(val x: Int, val y: Int) {


    fun path(direction: String): List<Coordinate> {

        when (direction[0]) {
            'U' -> {
                return (this.y..(this.y + direction.substring(1).toInt()))
                    .map { yCoord -> Coordinate(this.x, yCoord) } - this
            }
            'D' -> {
                return ((this.y - direction.substring(1).toInt())..(this.y))
                    .map { yCoord -> Coordinate(this.x, yCoord) } - this
            }
            'R' -> {
                return (this.x..(this.x + direction.substring(1).toInt()))
                    .map { xCoord -> Coordinate(xCoord, this.y) } - this
            }
            'L' -> {
                return ((this.x - direction.substring(1).toInt())..this.x)
                    .map { xCoord -> Coordinate(xCoord, this.y) } - this
            }
            else -> throw RuntimeException("Unknown direction")
        }
    }

    fun destination(direction: String): Coordinate {
        when (direction[0]) {
            'U' -> return Coordinate(this.x, this.y + direction.substring(1).toInt())
            'D' -> return Coordinate(this.x, this.y - direction.substring(1).toInt())
            'R' -> return Coordinate(this.x + direction.substring(1).toInt(), this.y)
            'L' -> return Coordinate(this.x - direction.substring(1).toInt(), this.y)
            else -> throw RuntimeException("Unknown direction")
        }
    }

    override fun toString(): String {
        return "(x=$x, y=$y)"
    }

    fun manhattanDistance(): Int {
        return abs(this.x) + abs(this.y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}