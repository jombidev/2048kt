package dev.jombi.kt2048

enum class Direction(val offsetX: Int, val offsetY: Int, val offset: Int, val increment: Int) {
    EAST(1, 0, 4, -1), WEST(-1, 0, -1, 1), SOUTH(0, -1, 4, -1), NORTH(0, 1, -1, 1)
}