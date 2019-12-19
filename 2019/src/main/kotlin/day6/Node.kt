package day6

class Node(val name: String, val orbits: Int) {

    private val children: MutableList<Node> = mutableListOf()
    private var parent: Node? = null

    fun addChild(name: String): Node {
        var child = Node(name, this.orbits + 1)
        child.parent = this
        children.add(child)
        return child
    }

    fun isAncestorOf(name: String): Boolean {
        var nodesToCheck = this.children.toList()
        while (nodesToCheck.isNotEmpty()) {
            if (nodesToCheck.map { it.name }.contains(name)) {
                return true
            }
            nodesToCheck = nodesToCheck.flatMap { it.children }
        }
        return false
    }

    fun getChildren(): List<Node> {
        return children.toList()
    }

    fun hasParent(): Boolean {
        return parent != null
    }

    fun getParent(): Node? {
        return this.parent
    }

    override fun toString(): String {
        return "Node(name='$name', orbits=$orbits, children=$children)"
    }

}