package dev.jombi.kt2048

class GameFinished(num: Int) : RuntimeException("Game finished with '$num'") {
}