package dev.jombi.kt2048

class GameFinished(num: Int) : RuntimeException("Game Over! your maximum is '$num'") {
}