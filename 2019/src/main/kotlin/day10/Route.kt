package day10

import common.Point
import java.math.BigDecimal
import java.math.RoundingMode

class Route(val a: Point, val b: Point) {

    fun containingPoints(): List<Point> {
        when {
            isHorizontallyAligned() -> {
                return IntRange(minOf(a.x, b.x) + 1, maxOf(a.x, b.x) - 1)
                    .map { Point(it, a.y)}
            }
            isVerticallyAligned() -> {
                return IntRange(minOf(a.y, b.y) + 1, maxOf(a.y, b.y) - 1)
                    .map { Point(a.x, it)}
            }
            else -> {
                val equation = LinearEquation(this)
//                println("Equation for $this is $equation")
                return IntRange(minOf(a.x, b.x) + 1, maxOf(a.x, b.x) - 1)
//                    .map { println("Range is $it"); it }
                    .filter { isNaturalNumber(equation.call(it).setScale(2, RoundingMode.HALF_UP)) }
                    .map { Point(it, equation.call(it).setScale(0, RoundingMode.HALF_UP).toInt()) }
            }
        }
    }

    fun isNaturalNumber(number: BigDecimal): Boolean {
//        println("Is natural number: $number")
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
    }

    fun isHorizontallyAligned(): Boolean {
        return a.y == b.y
    }

    fun isVerticallyAligned(): Boolean {
        return a.x == b.x
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Route

        if(a == other.a && b == other.b) return true
        if(a == other.b && b == other.a) return true

        return false
    }

    override fun hashCode(): Int {
        var result = a.hashCode()
        result = 31 * (result + b.hashCode())
        return result
    }

    override fun toString(): String {
        return "Route(a=$a, b=$b)"
    }


}
