package day12


class Moon(var position: Vector) {
    var velocity = Vector(0, 0, 0)
    override fun toString(): String {
        return "pos=$position, vel=$velocity"
    }

    fun move() {
        position.x += velocity.x
        position.y += velocity.y
        position.z += velocity.z
    }

    fun totalEnergy(): Long {
        return position.energy() * velocity.energy()
    }

    fun checksum(): String {
        return position.checksum() + velocity.checksum()
    }
}