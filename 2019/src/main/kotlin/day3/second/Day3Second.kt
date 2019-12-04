package day3.second

import common.InputReader

fun main() {
    val grid: MutableMap<Coordinate, MutableList<Visit>> = HashMap()

    val firstInput: List<String> = InputReader().fromFileCommaSeparated("input/day3/firstInput1.txt")
    travelCircuit("a", firstInput, grid)

    val secondInput: List<String> = InputReader().fromFileCommaSeparated("input/day3/firstInput2.txt")
    travelCircuit("b", secondInput, grid)

    val closest = grid
        .filter { entry -> entry.value.size == 2 }
        .map { entry -> entry.value.map { it.steps }.sum() }
        .min()
    println(closest)
}

private fun travelCircuit(
    circuitName: String,
    input: List<String>,
    grid: MutableMap<Coordinate, MutableList<Visit>>
) {
    var current = Coordinate(0, 0)
    var steps = 1
    input.forEach { direction ->
        current.path(direction).forEach { coordinate ->
            val visits: MutableList<Visit> = grid.getOrPut(coordinate, { mutableListOf() })
            if (visits.filter { it.circuit == circuitName }.isEmpty()) {
                visits.add(Visit(circuitName, steps))
            }
            steps++
        }
        current = current.destination(direction)
    }
}

private class Coordinate(val x: Int, val y: Int) {


    fun path(direction: String): List<Coordinate> {

        when (direction[0]) {
            'U' -> {
                return (this.y..(this.y + direction.substring(1).toInt()))
                    .map { yCoord -> Coordinate(this.x, yCoord) } - this
            }
            'D' -> {
                return ((this.y - direction.substring(1).toInt())..(this.y))
                    .reversed()
                    .map { yCoord -> Coordinate(this.x, yCoord) } - this
            }
            'R' -> {
                return (this.x..(this.x + direction.substring(1).toInt()))
                    .map { xCoord -> Coordinate(xCoord, this.y) } - this
            }
            'L' -> {
                return ((this.x - direction.substring(1).toInt())..this.x)
                    .reversed()
                    .map { xCoord -> Coordinate(xCoord, this.y) } - this
            }
            else -> throw RuntimeException("Unknown direction")
        }
    }

    fun destination(direction: String): Coordinate {
        return when (direction[0]) {
            'U' -> Coordinate(this.x, this.y + direction.substring(1).toInt())
            'D' -> Coordinate(this.x, this.y - direction.substring(1).toInt())
            'R' -> Coordinate(this.x + direction.substring(1).toInt(), this.y)
            'L' -> Coordinate(this.x - direction.substring(1).toInt(), this.y)
            else -> throw RuntimeException("Unknown direction")
        }
    }

    override fun toString(): String {
        return "(x=$x, y=$y)"
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

private class Visit(val circuit: String, val steps: Int) {
}