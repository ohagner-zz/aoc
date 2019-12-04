package day4

import kotlinx.coroutines.flow.*


suspend fun main() {
    val start: Long = System.currentTimeMillis()
    val potentialPasswords = (168630..718098).asFlow()
        .filter { neverDecrease(it) }
        .filter { adjacentEqual(it) }
        .count()
    println(potentialPasswords)
    println("Finished in ${System.currentTimeMillis() - start} ms" )
}

suspend fun neverDecrease(password: Int): Boolean {
    val flow: Flow<Char> = password.toString().toList().asFlow()
    return flow
        .map { it.toInt()}
        .take(5)
        .zip(flow
            .map { it.toInt()}
            .drop(1))
        { a,b ->
            a <= b
        }.reduce { acc, value -> acc && value}
}

suspend fun adjacentEqual(password: Int): Boolean {
    val flow: Flow<Char> = password.toString().toList().asFlow()
    return flow
        .scan(Pair('x', 'y'), { acc, value ->
        Pair(value, acc.first)
        })
        .map { pair -> pair.first == pair.second }
        .reduce { acc, value -> acc || value }
}
