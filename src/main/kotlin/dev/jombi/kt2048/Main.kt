package dev.jombi.kt2048

import kotlin.system.exitProcess

private val br = System.`in`.bufferedReader()
private var f = Field()

fun main() {
    while (true) {
        println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
        print(f.buildConsoleUI())
        try {
            val t = br.readLine().lowercase()
            val ts = t.split(' ')
            when (ts[0]) {
                "restart", "reset" -> f = Field()
                "left", "west", "l" -> f.move(Direction.WEST)
                "right", "east", "r" -> f.move(Direction.EAST)
                "south", "down", "d" -> f.move(Direction.SOUTH)
                "north", "up", "u" -> f.move(Direction.NORTH)
                "set" -> f.set(ts[1].toInt(), ts[2].toInt(), ts[3].toInt())
                "exit", "quit", "q" -> exitProcess(0)
                else -> continue
            }
        } catch (e: GameFinished) {
            println(e.message)
            println("Press enter to reset")
            br.readLine()
            f = Field()
        } catch (e: GameCleared) {
            println(e.message)
            println("Press enter to reset")
            br.readLine()
            f = Field()
        }

    }
}