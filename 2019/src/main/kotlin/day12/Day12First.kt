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
    repeat(1000) {
        allMoonPairs(moons).forEach { applyGravity(it.first, it.second) }
        moons.forEach { it.move() }
        moons.forEach { println(it) }
    }

    val totalEnergy = moons.stream()
        .map { it.totalEnergy() }
        .reduce { acc, value -> acc + value }
        .get()
    println("Total energy is $totalEnergy")


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