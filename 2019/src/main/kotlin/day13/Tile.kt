package day13


enum class Tile(val printChar: Char, val value: Long) {

    EMPTY(' ', 0), WALL('W', 1),BLOCK('B', 2),
    PADDLE('P', 3), BALL('O', 4);

    override fun toString(): String{
        return "$printChar"
    }

    companion object {
        fun fromValue(value: Long): Tile {
            return Tile.values()
                .toList()
                .stream()
                .filter() { it.value == value }
                .findAny()
                .orElseThrow { RuntimeException("Unknown Tile value $value") }

        }
    }
}

