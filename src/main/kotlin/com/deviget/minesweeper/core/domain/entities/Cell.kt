package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.CellVisibility.HIDDEN
import com.deviget.minesweeper.core.domain.entities.CellVisibility.VISIBLE

enum class CellVisibility {
	HIDDEN,
	VISIBLE
}

data class CellValue(val value: Int) {
	init {
		require(value >= 0) {
			"Cell value must be equal or greater than 0"
		}
	}
}

class Cell(
		val position: Position,
		private var visibility: CellVisibility = HIDDEN,
		val cellValue: CellValue
) {

	val adjacentPositions get() = position.adjacentPositions
	val value get() = cellValue.value

	fun reveal() {
		visibility = VISIBLE
	}

	fun isVisible() = visibility == VISIBLE

}