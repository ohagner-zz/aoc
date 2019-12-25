package day8.second

import common.InputReader
import common.Point
import java.util.function.BiFunction

fun main() {
    val input = InputReader().fromFileAsString("input/day8/first.txt")
//    val input = "0222112222120000"
    val maxWidth = 25
    val maxHeight = 6
    val layers = input
        .chunked(maxWidth * maxHeight)
        .map { Layer(maxWidth, maxHeight).init(it) }

    val identityMap: MutableMap<Point, Int> = mutableMapOf()
    layers.first().getCoordinates()
        .forEach { identityMap.put(it, 2) }
    val identity = Layer(maxWidth, maxHeight, identityMap)

    layers.stream()
        .reduce(identity, { acc, value -> acc.flatten(value) })
        .print()

}



class Layer(private val width: Int, private val height: Int) {

    constructor(width: Int, height: Int, data: MutableMap<Point,Int>): this(width, height) {
        pixels = data
    }

    fun flatten(other: Layer): Layer {
        val mergedMap: MutableMap<Point, Int> = mutableMapOf()
        pixels.keys.stream()
            .map { println(it);  it }
            .forEach {point: Point ->
                when(this.getValue(point)) {
                    2 -> mergedMap[point] = other.getValue(point)
                    1,0 -> mergedMap[point] = this.getValue(point)
//                    0 -> mergedMap[point] = this.getValue(point)
                    else -> RuntimeException("Unknown pixel value")
                }
            }
        val flattened = Layer(this.width, this.height, mergedMap)
        flattened.print()
        return flattened
    }

    private fun getValues(): MutableList<Int> {
        return pixels.values.toMutableList()
    }

    fun getCoordinates(): List<Point> {
        return pixels.keys.toList()
    }

    fun occurrencesOf(value: Int): Long {
        val total = getValues()
            .stream()
            .filter { it == value }
            .count()
//        println("Total : $total")
        return total
    }

    fun init(values: String): Layer {
        values
            .toList()
            .map { it.toString().toInt() }
            .forEach { addPixel(it) }
        return this
    }

    private var position = Point(1,1)
    private var pixels: MutableMap<Point, Int> = mutableMapOf()

    fun addPixel(value: Int) {
        pixels.put(position, value)
        movePosition()
    }

    fun getValue(point: Point): Int {
        return pixels[point]!!
    }

    private fun movePosition(): Point {
        if(position.x >= width && position.y < height) {
            position = Point(1, position.y + 1)
        } else {
            position = Point(position.x + 1, position.y)
        }
        return position
    }

    fun print() {
        (1..(height)).forEach { yCoord ->
            (1..width).forEach { xCoord ->
                if(getValue(Point(xCoord, yCoord)) == 1) {
                    print("*")
                } else {
                    print(" ")
                }
            }
            println()
        }
        println()
    }
}
