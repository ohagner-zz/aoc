package day12


fun main() {
    /*
    <x=-6, y=2, z=-9>
<x=12, y=-14, z=-4>
<x=9, y=5, z=-6>
<x=-1, y=-4, z=9>
     */
    val moons= listOf(
        Moon(Vector(-6, 2, -9)),
        Moon(Vector(12, -14, -4)),
        Moon(Vector(9, 5, -6)),
        Moon(Vector(-1, -4, 9))
    )
//
//    val moons= listOf(
//        Moon(Vector(-1, 0, 2)),
//        Moon(Vector(2, -10, -7)),
//        Moon(Vector(4, -8, 8)),
//        Moon(Vector(3, 5, -1))
//    )

    /*
    <x=-1, y=0, z=2>
<x=2, y=-10, z=-7>
<x=4, y=-8, z=8>
<x=3, y=5, z=-1>
     */

    var xStartPositions = moons.map { it.position.x }.joinToString()
    var yStartPositions = moons.map { it.position.y }.joinToString()
    var zStartPositions = moons.map { it.position.z }.joinToString()
    var repeatedPositions = mutableMapOf<String, Long>()

    var count: Long = 1L
//    while(count < 2772) {
    while(repeatedPositions.size < 3) {
        allMoonPairs(moons).forEach { applyGravity(it.first, it.second) }
        moons.forEach { it.move() }
        count++
        var xPositions = moons.map { it.position.x }.joinToString()
        var yPositions = moons.map { it.position.y }.joinToString()
        var zPositions = moons.map { it.position.z }.joinToString()
        if(xPositions.equals(xStartPositions) && !repeatedPositions.contains("x")) {
            println("X repeated in $count, positions: $xPositions")
            repeatedPositions.set("x", count)
        }
        if(yPositions.equals(yStartPositions) && !repeatedPositions.contains("y")) {
            println("Y repeated, positions: $yPositions")
            repeatedPositions.set("y", count)
        }
        if(zPositions.equals(zStartPositions) && !repeatedPositions.contains("z")) {
            println("Z repeated, positions: $zPositions")
            repeatedPositions.set("z", count)
        }

    }
    moons.forEach { println(it.position)}
    println(repeatedPositions)
    println("Repeated in $count iterations")

    val xyCommon = leastCommonMultiple(repeatedPositions.get("x")!!, repeatedPositions.get("y")!!)
    println("xyCommon: $xyCommon")
    val totalCommon = leastCommonMultiple(repeatedPositions.get("z")!!, xyCommon)
    println("Total common $totalCommon")


}

private fun greatestCommonDivisor(a: Long, b: Long): Long {
    if (a == 0L) {
        return b
    }
    return greatestCommonDivisor(b % a, a)
}

private fun leastCommonMultiple(a: Long, b: Long): Long {
    return (a * b) / greatestCommonDivisor(a, b)
}

private fun allMoonPairs(moons: List<Moon>): List<Pair<Moon, Moon>> {
    val availableMoons = moons.toMutableList()
    val pairs = mutableListOf<Pair<Moon, Moon>>()
    do {
        val left = availableMoons.removeAt(0)
        availableMoons.forEach { pairs.add(Pair(left, it)) }

    } while (availableMoons.size >= 2)
    return pairs
}

private fun applyGravity(first: Moon, second: Moon) {
    val xVelocityChange = calculateVelocityChange(Pair(first.position.x, second.position.x))
    first.velocity.x += xVelocityChange.first
    second.velocity.x += xVelocityChange.second
    val yVelocityChange = calculateVelocityChange(Pair(first.position.y, second.position.y))
    first.velocity.y += yVelocityChange.first
    second.velocity.y += yVelocityChange.second
    val zVelocityChange = calculateVelocityChange(Pair(first.position.z, second.position.z))
    first.velocity.z += zVelocityChange.first
    second.velocity.z += zVelocityChange.second
}

private fun calculateVelocityChange(axisPosition: Pair<Long, Long>): Pair<Long, Long> {
    return when {
        axisPosition.first > axisPosition.second -> {
            Pair(-1, 1)
        }
        axisPosition.first < axisPosition.second -> {
            Pair(1, -1)
        }
        else -> {
            Pair(0, 0)
        }
    }
}