package com.deviget.minesweeper.core.domain.entities

data class Position(val x: Int, val y: Int, private val edge: Edge) {
	init {
		require(x >= 0) {
			"x must be equal or greater than 0 "
		}
		require(y >= 0) {
			"y must be equal or greater than 0 "
		}
		require(x <= edge.xLimit) {
			"$x for x is out of bounds"
		}
		require(y <= edge.yLimit) {
			"$y for y is out of bounds"
		}
	}

	val upperLeft
		get(): Position? = try {
			Position(x - 1, y - 1, edge)
		} catch (exception: Exception) {
			null
		}

	val upper
		get(): Position? = try {
			Position(x, y - 1, edge)
		} catch (exception: Exception) {
			null
		}

	val upperRight
		get() : Position? = try {
			Position(x + 1, y - 1, edge)
		} catch (exception: Exception) {
			null
		}

	val left
		get() : Position? = try {
			Position(x - 1, y, edge)
		} catch (exception: Exception) {
			null
		}

	val right
		get(): Position? = try {
			Position(x + 1, y, edge)
		} catch (exception: Exception) {
			null
		}

	val lowerLeft
		get(): Position? = try {
			Position(x - 1, y + 1, edge)
		} catch (exception: Exception) {
			null
		}

	val lower
		get(): Position? = try {
			Position(x, y + 1, edge)
		} catch (exception: Exception) {
			null
		}

	val lowerRight
		get(): Position? = try {
			Position(x + 1, y + 1, edge)
		} catch (exception: Exception) {
			null
		}
}