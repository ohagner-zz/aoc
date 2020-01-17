package day12

import kotlin.math.abs

class Vector(var x: Long, var y: Long, var z: Long) {

    fun energy(): Long {
        return abs(x) + abs(y) + abs(z)
    }

    fun checksum(): String {
        return "$x$y$z"
    }

    override fun toString(): String {
        return "<x=$x, y=$y, z=$z>"
    }
}
