package common

import java.math.BigDecimal
import kotlin.math.*


class Point(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    fun angleTo(point: Point): Double {
        val angle = atan2((point.y - this.y).toDouble(), (point.x - this.x).toDouble())
        return angle
    }

    fun distanceTo(point: Point): Double {
        return hypot(abs(point.x - this.x).toDouble(), abs(point.y - this.y).toDouble())
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Point(x=$x, y=$y)"
    }


}