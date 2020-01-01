package day10

import common.InputReader
import common.Point
import java.math.BigDecimal


fun main() {
    //Läs in alla asteroider i en karta
    //För varje asteroid, kolla hur många man kan se och lägg in i svars-map <Point, Int>
    //Metod för att hämta koordinater mellan två punkter
    val asteroids = mutableListOf<Point>()

    val input = InputReader().fromFileRowSeparated("input/day10/input.txt")
    input.forEachIndexed { y, row ->
        row.toList().forEachIndexed { x, value ->
            println("[$x,$y] = $value")
            if(value.equals('#')) {
                asteroids.add(Point(x,y))
            }
        }
    }

    val max = asteroids.stream()
//        val point = Point(4,0)
        .map { point -> Pair(point, calculateClearPaths(point, asteroids.toMutableList())) }
        .max { p1, p2 -> p1.second.compareTo(p2.second) }
        .get()

    println("Max in ${max.first} with ${max.second} paths")




}

fun calculateClearPaths(point: Point, asteroids: MutableList<Point>): Long {
    asteroids.remove(point)
    return asteroids.stream()
        .map { asteroid -> Route(point, asteroid) }
        .map { route ->
//            println("Checking route $route")
            route.containingPoints().stream()
//                .map { println("Containing: $it"); it }
                .filter { asteroids.contains(it) }
//                .map { println("Asteroids contain $it");it}
                .count()
        }
//        .map { println("Obstacles: $it"); it }
        .filter { obstacles -> obstacles == 0L }
        .count()
}
