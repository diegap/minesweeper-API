package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.CellVisibility.HIDDEN
import com.deviget.minesweeper.core.domain.entities.CellVisibility.VISIBLE
import com.deviget.minesweeper.core.domain.exceptions.CellCannotBeRevealedException
import com.deviget.minesweeper.core.domain.exceptions.GameOverException

enum class CellVisibility {
	HIDDEN,
	VISIBLE
}

data class CellValue(val value: Int)

interface CellBehaviour {
	fun reveal(): Int
}

class MinedCell(val cell: CellBehaviour) : CellBehaviour {
	override fun reveal() = throw GameOverException()
}

class FlaggedCell(val cell: CellBehaviour) : CellBehaviour {
	override fun reveal() = throw CellCannotBeRevealedException()
}

class QuestionMarkedCell(val cell: CellBehaviour) : CellBehaviour {
	override fun reveal() = cell.reveal()
}

class Cell(
		val position: Position,
		private var visibility: CellVisibility = HIDDEN,
		val cellValue: CellValue
) : CellBehaviour {

	val adjacentPositions get() = position.adjacentPositions
	val value get() = cellValue.value

	override fun reveal(): Int {
		visibility = VISIBLE
		return cellValue.value
	}

	fun isVisible() = visibility == VISIBLE

}