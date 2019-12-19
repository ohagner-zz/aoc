package common

import java.io.File
import java.nio.charset.Charset


class InputReader {

    fun fromFileRowSeparated(filename: String): List<String> {
        return File(Thread.currentThread().contextClassLoader.getResource(filename).toURI()).readLines(Charset.forName("UTF-8"))
    }

    fun fromFileCommaSeparated(filename: String): List<String> {
        return fromCommaSeparated(File(Thread.currentThread().contextClassLoader.getResource(filename).toURI()).readText(Charset.forName("UTF-8")))
    }

    fun fromCommaSeparated(value: String): List<String> { return value.split(",")}
}