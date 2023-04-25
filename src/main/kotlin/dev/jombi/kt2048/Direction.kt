package dev.jombi.kt2048

enum class Direction(val offsetX: Int, val offsetY: Int) {
    EAST(1, 0), WEST(-1, 0), SOUTH(0, -1), NORTH(0, 1)
}