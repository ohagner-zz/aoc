package day6

import common.InputReader

fun main() {

    val nodes: MutableMap<String, Node> = mutableMapOf()
    val root = Node("COM", 0)
    nodes.put(root.name, root)

    var orbits: List<Orbit> = InputReader()
        .fromFileRowSeparated("input/day6/input.txt")
        .map { it.split(")") }
        .map { Orbit(it.first(), it.last()) }

    while(orbits.isNotEmpty()) {
        val orbit = orbits.get(0)
        orbits = orbits.drop(1)
        val parent = nodes.get(orbit.parent)
        if(parent != null) {
            val childNode = parent.addChild(orbit.child)
            nodes.put(childNode.name, childNode)
        } else {
            orbits = orbits.plus(orbit)
        }
    }
    println(nodes.values.stream()
        .mapToInt { it.orbits }
        .sum())
}


