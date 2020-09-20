package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.CellVisibility.HIDDEN
import com.deviget.minesweeper.core.domain.entities.CellVisibility.VISIBLE

enum class CellVisibility {
	HIDDEN,
	VISIBLE
}

class Cell(
		val position: Position,
		private var visibility: CellVisibility = HIDDEN
) {

	fun reveal() {
		visibility = VISIBLE

	}

	fun isVisible() = visibility == VISIBLE

	constructor(position: Position, content: String) : this(position)

	val adjacentPositions
		get() = mutableSetOf<Position?>().apply {
			add(position.upperLeft)
			add(position.upper)
			add(position.upperRight)
			add(position.left)
			add(position.right)
			add(position.lowerLeft)
			add(position.lower)
			add(position.lowerRight)
		}.filterNotNull().toSet()
}