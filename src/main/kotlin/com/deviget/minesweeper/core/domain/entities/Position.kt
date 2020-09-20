package com.deviget.minesweeper.core.domain.entities

data class Position(
		val x: Int,
		val y: Int,
		private val edge: Edge
) {
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

	val adjacentPositions
		get() = mutableSetOf<Position?>().apply {
			add(upperLeft)
			add(upper)
			add(upperRight)
			add(left)
			add(right)
			add(lowerLeft)
			add(lower)
			add(lowerRight)
		}.filterNotNull().toSet()

	private val upperLeft
		get(): Position? = try {
			Position(x - 1, y - 1, edge)
		} catch (exception: Exception) {
			null
		}

	private val upper
		get(): Position? = try {
			Position(x, y - 1, edge)
		} catch (exception: Exception) {
			null
		}

	private val upperRight
		get() : Position? = try {
			Position(x + 1, y - 1, edge)
		} catch (exception: Exception) {
			null
		}

	private val left
		get() : Position? = try {
			Position(x - 1, y, edge)
		} catch (exception: Exception) {
			null
		}

	private val right
		get(): Position? = try {
			Position(x + 1, y, edge)
		} catch (exception: Exception) {
			null
		}

	private val lowerLeft
		get(): Position? = try {
			Position(x - 1, y + 1, edge)
		} catch (exception: Exception) {
			null
		}

	private val lower
		get(): Position? = try {
			Position(x, y + 1, edge)
		} catch (exception: Exception) {
			null
		}

	private val lowerRight
		get(): Position? = try {
			Position(x + 1, y + 1, edge)
		} catch (exception: Exception) {
			null
		}
}