package day8.first

import common.InputReader
import common.Point

fun main() {
//    val input = InputReader().fromFileAsString("input/day8/first.txt")
    val input = "0222112222120000"
    val maxWidth = 2 //25
    val maxHeight = 2 //6
    val layers = input
        .chunked(maxWidth * maxHeight)
        .map { Layer(maxWidth, maxHeight).init(it) }

    val sum: Long = layers.stream()
        .min{ a, b -> a.occurrencesOf(0).compareTo(b.occurrencesOf(0)) }
        .map { it.occurrencesOf(1) * it.occurrencesOf(2) }
        .orElse(0)
    println("Value is " + sum)
}



class Layer(private val width: Int, private val height: Int) {

    constructor(width: Int, height: Int, data: MutableMap<Point,Int>): this(width, height) {
        pixels = data
    }

    fun flatten(other: Layer): Layer {
        val mergedMap: MutableMap<Point, Int> = mutableMapOf()
        pixels.keys.stream()
            .forEach {point: Point ->
                when(this.getValue(point)) {
                    0 -> mergedMap[point] = other.getValue(point)
                    1,2 -> mergedMap[point] = this.getValue(point)
                    else -> RuntimeException("Unknown pixel value")
                }
            }
        return Layer(this.width, this.height, mergedMap)
    }

    private fun getValues(): MutableCollection<Int> {
        return pixels.values
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
        (1..(position.y)).forEach { yCoord ->
            (1..width).forEach { xCoord ->
                print(getValue(Point(xCoord, yCoord)))
            }
            println()
        }
    }
}
