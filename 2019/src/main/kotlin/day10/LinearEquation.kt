package day10

import java.math.BigDecimal
import java.math.RoundingMode


class LinearEquation(route: Route) {

    val directionCoefficent= calculateDirectionCoefficient(route)
    val verticalOffset = (directionCoefficent.multiply(BigDecimal(route.a.x))) - BigDecimal((route.a.y)).setScale(3, RoundingMode.HALF_UP)

    private fun calculateDirectionCoefficient(route: Route): BigDecimal {
        return BigDecimal(route.b.y - route.a.y).divide(BigDecimal(route.b.x - route.a.x), 3, RoundingMode.HALF_UP)
    }

    fun call(x: Int): BigDecimal {
        return directionCoefficent.multiply(BigDecimal(x)).subtract(verticalOffset)
    }

    override fun toString(): String {
        return "LinearEquation($directionCoefficent * x + $verticalOffset"
    }


}