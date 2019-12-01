package day1

import common.InputReader
import kotlin.math.floor


fun main() {
    val moduleFuelSum: Int = InputReader().fromFile("input/day1/first.txt")
        .map { it.toDouble() }
        .map { it.div(3) }
        .map { floor(it).toInt() }
        .map { it - 2 }
        .sum()

    println("Sum is $moduleFuelSum")
}


