package dev.jombi.kt2048

import dev.jombi.kt2048.field.Position

class Field {
    private val positions = Array(4 * 4) { Position(it % 4, it / 4, 0) }
    private val w = arrayOf(2, 2, 2, 2, 2, 2, 2, 2, 4, 4)
    private var maxLen = 0
    init {
        new()
        new()
    }

    private fun Array<Position>.merge(d: Direction) {
        if (d.offsetY != 0) {
            for (i in 0 until 4) {
                for (mergeCount in 0 until 4) {
                    val posl = getXAxisPosList(i).filter { it.num != 0 }
                    if (posl.distinctBy { it.num }.size == posl.size) break
                    for ((x, y, num) in posl) {
                        modPosition(posl, x, y, num, 0, d.offsetY)
                    }
                }
            }
        } else {
            for (i in 0 until 4) {
                for (mergeCount in 0 until 4) {
                    val posl = getYAxisPosList(i).filter { it.num != 0 }
                    if (posl.distinctBy { it.num }.size == posl.size) break
                    for ((x, y, num) in posl) {
                        modPosition(posl, x, y, num, d.offsetX, 0)
                    }
                }
            }
        }
    }

    private fun Array<Position>.modPosition(posl: List<Position>, x: Int, y: Int, num: Int, offsetX: Int, offsetY: Int) {
        if (posl.count { it.num == num } <= 1) return
        val cur = getPosition(x, y)
        if (cur.num == 0) return
        if (x - offsetX !in 0 until 4) return
        if (y - offsetY !in 0 until 4) return
        val right = getPosition(x - offsetX, y - offsetY)
        if (right.num != cur.num) return
        setPosition(x, y, cur.num shl 1)
        setPosition(right.x, right.y, 0)
    }

    fun move(d: Direction) {
        val copy = arrayOf(*positions)
        positions.move(d)
        if (positions.contentEquals(copy)) return

        new()
        move++
    }

    fun set(x: Int, y: Int, num: Int) {
        positions.setPosition(x, y, num)
    }

    fun Array<Position>.gravity(d: Direction) {
        val gravities = Array(4) { d.offset }
        val thing = filter { it.num != 0 }.toMutableList()
        if (d.offset > 0) thing.reverse()
        for ((x, y, num) in thing) {
            val offsetT = if (d.offsetY == 0) y else x
            gravities[offsetT] += d.increment
            val grav = maxOf(gravities[offsetT], 0)
            val targetX = if (d.offsetX != 0) grav else x
            val targetY = if (d.offsetY != 0) grav else y
            setPosition(x, y, 0)
            setPosition(targetX, targetY, num)
        }
    }

    private fun Array<Position>.move(d: Direction) {
        repeat(4) {
            gravity(d)
            merge(d)
        }
        gravity(d)
    }

    private var move = 0

    private fun emulateFinish(): Boolean {
        if (positions.any { it.num == 2048 }) {
            throw GameCleared(move)
        }
        if (positions.any { it.num == 0 }) return false
        val m = arrayOf(*positions)
        for (d in Direction.values()) {
            m.move(d)
            if (m.any { it.num == 0 })
                return false
        }
        return true

    }

    private fun Array<Position>.setPosition(x: Int, y: Int, num: Int) {
        set(x + y * 4, Position(x, y, num))
        maxLen = maxOf("$num".length, maxLen)
    }

    private fun getPosition(x: Int, y: Int) = positions[x + y * 4]
    private fun getXAxisPosList(x: Int) = positions.filter { it.x == x }
    private fun getYAxisPosList(y: Int) = positions.filter { it.y == y }
    fun new() {
        val rng = positions.filter { it.num == 0 }.random()
        positions.setPosition(rng.x, rng.y, w.random())
        if (emulateFinish()) {
            throw GameFinished(positions.maxOf { it.num })
        }
    }

    fun buildConsoleUI(): String {
        val sb = StringBuilder()
        val s = "-----" + "-".repeat(maxLen-1)
        for ((x, _, num) in positions) {
            val minus = maxLen - "$num".length
            if (x == 0) sb.append("$s $s $s $s\n")
            sb.append("| ${" ".repeat(minus)}$num | ")
            if (x == 3) sb.append('\n')
        }
        sb.append("$s $s $s $s\n")
        sb.append("> ")
        return "$sb"
    }
}