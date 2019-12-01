package common

import java.io.File
import java.nio.charset.Charset


class InputReader {

    fun fromFile(filename: String): List<String> {
        return File(Thread.currentThread().contextClassLoader.getResource(filename).toURI()).readLines(Charset.forName("UTF-8"))
    }
}