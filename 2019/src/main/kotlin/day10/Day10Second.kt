package day10

import common.InputReader
import common.Point
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


fun main() {
    val base = Point(13,17)
//    val base = Point(11,13)
    val asteroids = mutableMapOf<Double, MutableList<Point>>()

    val input = InputReader().fromFileRowSeparated("input/day10/second/input.txt")
    input.forEachIndexed { y, row ->
        row.toList().forEachIndexed { x, value ->
//            println("[$x,$y] = $value")
            if(value.equals('#')) {
                val point = Point(x,y)
                if(!point.equals(base)) {
                    var angle = base.angleTo(point)
                    val rotated = atan2(sin(angle - Math.PI / 2), cos(angle - Math.PI / 2))
                    asteroids.merge(
                        rotated,
                        mutableListOf(point),
                        { existingList, toAdd -> existingList.addAll(toAdd); existingList })
                }
            }
        }
    }

    val asteroidsByLap = mutableMapOf<Double, Point>()

    asteroids
        .toSortedMap(compareBy { it })
        .forEach { angle, pointList ->
            pointList
                .sortedBy { it.distanceTo(base) }
                .forEachIndexed { lap, point ->
//                    println("Putting $point at ${angle + (lap * 2 * Math.PI)}")
                    asteroidsByLap.put(angle + (lap * 2 * Math.PI), point)
                }

        }

    println(asteroidsByLap.toSortedMap().values.toList().get(199))

}