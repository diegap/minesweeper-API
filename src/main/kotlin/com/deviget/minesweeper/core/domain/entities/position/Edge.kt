package com.deviget.minesweeper.core.domain.entities.position

data class Edge(val cols: Cols, val rows: Rows) {
	val xLimit get() = cols.value - 1
	val yLimit get() = rows.value - 1
}