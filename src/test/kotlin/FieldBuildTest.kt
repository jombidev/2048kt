package dev.jombi.kt2048

fun main() {
    val br = System.`in`.bufferedReader()
    val e = Field()
    for (i in 0 until 5) e.new()
    println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
    print(e.buildConsoleUI())
    br.readLine()
}