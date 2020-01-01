package day10

import common.Point
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.math.BigDecimal


internal class LinearEquationTest {

    @Test
    fun shouldCalculateEquation() {
        val simpleThroughOrigo = LinearEquation(Route(Point(0,0), Point(3, 3)))
        assertEquals(BigDecimal(5).setScale(3), simpleThroughOrigo.call(5))

        val increasing = LinearEquation(Route(Point(0,2), Point(2, 8)))
        assertEquals(BigDecimal(17).setScale(3), increasing.call(5))

        val decreasing = LinearEquation(Route(Point(2,2), Point(6, 0)))
        println(decreasing)
        assertEquals(BigDecimal(0.5).setScale(3), decreasing.call(5))
    }

}