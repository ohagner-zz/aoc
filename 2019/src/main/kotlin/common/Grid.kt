package common

import java.lang.StringBuilder


class Grid<V>(val defaultValue: V) {

    private var data = mutableMapOf<Point, V>()

    fun get(point: Point): V {
        return data.getOrDefault(point, defaultValue)
    }

    fun set(point: Point, value: V) {
        data[point] = value
    }

    fun values(): List<V> {
        return data.values.toList()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        val minY = data.keys.map { it.y }.min()
        val minX = data.keys.map { it.x }.min()
        val maxX = data.keys.map { it.x }.max()
        val maxY = data.keys.map { it.y }.max()
        (minY)!!.rangeTo(maxY!!).forEach { y ->
            (minX)!!.rangeTo(maxX!!).forEach { x ->
                sb.append(get(Point(x,y)).toString())
            }
            sb.append("\n")
        }
        return sb.toString()
    }


}