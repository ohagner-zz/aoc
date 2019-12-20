package day7.first

enum class ParameterMode {
    POSITION, IMMEDIATE;

    companion object Factory {

        fun fromValue(i: Char): ParameterMode {
            return when (i) {
                '0' -> ParameterMode.POSITION
                '1' -> ParameterMode.IMMEDIATE
                else -> throw RuntimeException("Invalid parameter mode value")
            }
        }
    }
}
