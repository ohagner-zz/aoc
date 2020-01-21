package common

import java.lang.StringBuilder
import java.util.*


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

    fun size(): Int {
        return data.size
    }

    fun entries(): MutableSet<MutableMap.MutableEntry<Point, V>> {
        return data.entries
    }


    override fun toString(): String {
        val sb = StringBuilder()
        val minY = Optional.ofNullable(data.keys.map { it.y }.min()).orElse(0)
        val minX = Optional.ofNullable(data.keys.map { it.x }.min()).orElse(0)
        val maxX = Optional.ofNullable(data.keys.map { it.x }.max()).orElse(0)
        val maxY = Optional.ofNullable(data.keys.map { it.y }.max()).orElse(0)
        (minY)!!.rangeTo(maxY!!).forEach { y ->
            (minX)!!.rangeTo(maxX!!).forEach { x ->
                sb.append(get(Point(x,y)).toString())
            }
            sb.append("\n")
        }
        return sb.toString()
    }


}