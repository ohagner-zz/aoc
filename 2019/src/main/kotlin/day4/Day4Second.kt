package day4

import kotlinx.coroutines.flow.*


suspend fun main() {
    val start: Long = System.currentTimeMillis()
    val potentialPasswords = (168630..718098).asFlow()
        .filter { neverDecrease(it) }
        .filter { pairNotPartOfLargerGroup(it) }
        .count()
    println(potentialPasswords)
    println("Finished in ${System.currentTimeMillis() - start} ms" )
}

private suspend fun neverDecrease(password: Int): Boolean {
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

private suspend fun adjacentEqual(password: Int): Boolean {
    val flow: Flow<Char> = password.toString().toList().asFlow()
    return flow
        .scan(Pair('x', 'y'), { acc, value ->
        Pair(value, acc.first)
        })
        .map { pair -> pair.first == pair.second }
        .reduce { acc, value -> acc || value }
}

private fun pairNotPartOfLargerGroup(password: Int): Boolean {
    val passwordChars: List<Char> = password.toString().toList()
    var previous: Char? = null
    var equalChars = 1
    passwordChars.forEach{ current ->
        if(current == previous) {
            equalChars++
        } else if(equalChars == 2) {
            return true
        } else {
            equalChars = 1
        }
        previous = current
    }
    return equalChars == 2
}