package day6

import common.InputReader
import java.lang.RuntimeException

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

    val you = nodes.get("YOU")!!
    val santa = nodes.get("SAN")!!
    var commonAncestor = findCommonAncestor(you, santa.name)

    println((you.orbits - commonAncestor.orbits - 1) + (santa.orbits - commonAncestor.orbits - 1))
}

fun findCommonAncestor(node: Node, name: String): Node {
    if(node.isAncestorOf(name)) {
        return node
    } else if(node.hasParent()) {
        return findCommonAncestor(node.getParent()!!, name)
    } else {
        throw RuntimeException("Reached root")
    }
}

