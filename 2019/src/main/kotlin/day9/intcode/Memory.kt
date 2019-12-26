package day9.intcode


class Memory {
    private val memoryMap = mutableMapOf<Long, Long>()

    fun get(key: Long): Long {
        return memoryMap.getOrDefault(key, 0)
    }

    fun set(key: Long, value: Long) {
        memoryMap[key] = value
    }

    fun getParameterValue(position: Long, relativeBase: Long, parameterMode: ParameterMode): Long {
        return when(parameterMode) {
            ParameterMode.POSITION -> get(position)
            ParameterMode.IMMEDIATE -> position
            ParameterMode.RELATIVE -> get(position + relativeBase)
        }
    }

    fun getOutputDestination(position: Long, relativeBase: Long, parameterMode: ParameterMode): Long {
        val positionValue = get(position)
        return when(parameterMode) {
            ParameterMode.POSITION -> positionValue
            ParameterMode.IMMEDIATE -> throw RuntimeException("Immediate mode not allowed for output")
            ParameterMode.RELATIVE -> positionValue + relativeBase
        }
    }
}