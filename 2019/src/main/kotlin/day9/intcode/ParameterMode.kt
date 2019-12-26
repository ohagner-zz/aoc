package day9.intcode

enum class ParameterMode {
    POSITION, IMMEDIATE, RELATIVE;

    companion object Factory {

        fun fromValue(i: Char): ParameterMode {
            return when (i) {
                '0' -> POSITION
                '1' -> IMMEDIATE
                '2' -> RELATIVE
                else -> throw RuntimeException("Invalid parameter mode value")
            }
        }
    }
}
