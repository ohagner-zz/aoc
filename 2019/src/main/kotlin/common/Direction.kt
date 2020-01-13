package common

import java.lang.RuntimeException


enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun turn(direction: Direction): Direction {
        return when(direction) {
            LEFT -> {
                when(this) {
                    UP -> LEFT
                    DOWN -> RIGHT
                    LEFT -> DOWN
                    RIGHT -> UP
                }
            }
            RIGHT -> {
                when(this) {
                    UP -> RIGHT
                    DOWN -> LEFT
                    LEFT -> UP
                    RIGHT -> DOWN
                }
            }
            else -> throw RuntimeException("You can only turn left or right")
        }
    }

}

