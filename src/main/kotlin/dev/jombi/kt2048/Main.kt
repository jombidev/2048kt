package dev.jombi.kt2048

import kotlin.system.exitProcess

private val br = System.`in`.bufferedReader()
private var f = Field()

fun main() {
    while (true) {
        println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
        print(f.buildConsoleUI())
        try {
            when (br.readLine().lowercase()) {
                "restart", "reset" -> f = Field()
                "left", "west", "l" -> f.move(Direction.WEST)
                "right", "east", "r" -> f.move(Direction.EAST)
                "south", "down", "d" -> f.move(Direction.SOUTH)
                "north", "up", "u" -> f.move(Direction.NORTH)
                "exit" -> exitProcess(0)
                else -> continue
            }
        } catch (e: GameFinished) {
            println(e.message)
            println("Press enter to reset")
            br.readLine()
            f = Field()
        }

    }
}