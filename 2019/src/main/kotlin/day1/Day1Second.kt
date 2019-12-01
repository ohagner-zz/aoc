package day1

import common.InputReader
import java.util.stream.Collectors.toList
import kotlin.math.floor


fun main() {
    val moduleFuelSum: Int = InputReader().fromFile("input/day1/first.txt")
        .map { it.toInt() }
        .map { moduleFuelSum(it) }
        .map { recursiveModuleFuelSum(it) }
        .sum()

    println("Sum is $moduleFuelSum")
}

fun recursiveModuleFuelSum(value: Int): Int {
    if(value <= 0) {
        return 0
    } else {
        return value +
            recursiveModuleFuelSum(moduleFuelSum(value))
    }
}

fun moduleFuelSum(value: Int): Int {
    return listOf(value)
        .map { it.toDouble() }
        .map { it.div(3) }
        .map { floor(it).toInt() }
        .map { it - 2 }
        .sum()
}
