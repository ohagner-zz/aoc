package day10

import common.Point
import junit.framework.TestCase.*
import org.junit.Test


internal class RouteTest {

    @Test
    fun testContainingPoints() {
        assertEquals(listOf(Point(1,1), Point(2,2)), Route(Point(3,3), Point(0,0)).containingPoints())
        assertEquals(listOf(Point(3,4)), Route(Point(0,0), Point(6,8)).containingPoints())
        assertEquals(listOf(Point(2,1)), Route(Point(1,0), Point(3,2)).containingPoints())
        assertEquals(listOf(Point(4,1)), Route(Point(4,0), Point(4,2)).containingPoints())
    }

    @Test
    fun testHorizontallyAligned() {
        assertTrue(Route(Point(1, 2), Point(5, 2)).isHorizontallyAligned())
        assertTrue(Route(Point(2, 2), Point(2, 2)).isHorizontallyAligned())
        assertTrue(Route(Point(1, -2), Point(-5, -2)).isHorizontallyAligned())

        assertFalse(Route(Point(0, 0), Point(0, 1)).isHorizontallyAligned())
        assertFalse(Route(Point(-5, -1), Point(5, 1)).isHorizontallyAligned())
    }

    @Test
    fun isVerticallyAligned() {
        assertTrue(Route(Point(0, 2), Point(0, -2)).isVerticallyAligned())
        assertTrue(Route(Point(0, 0), Point(0, 0)).isVerticallyAligned())
        assertTrue(Route(Point(-20, 3), Point(-20, 5)).isVerticallyAligned())

        assertFalse(Route(Point(-1, 3), Point(1, 0)).isVerticallyAligned())
    }


    @Test
    fun testEquals() {
        val route = Route(Point(0, 0), Point(1, 1))
        val reverseRoute = Route(Point(1, 1), Point(0, 0))
        val routes: Set<Route> = mutableSetOf(route, reverseRoute)
        assertTrue(routes.size == 1)
    }

    @Test
    fun testNotEqual() {
        val route = Route(Point(0, 0), Point(1, 1))
        val otherRoute = Route(Point(1, 1), Point(0, 1))
        val routes: Set<Route> = mutableSetOf(route, otherRoute)
        assertTrue(routes.size == 2)
    }

}