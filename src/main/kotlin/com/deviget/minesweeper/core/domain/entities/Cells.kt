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

interface Cell {
	fun reveal(): Int
	fun getPosition(): Position
	fun isVisible(): Boolean
}

class MinedCell(private val cell: Cell) : Cell {
	override fun reveal() = throw GameOverException()
	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}
}

class FlaggedCell(val cell: Cell) : Cell {
	override fun reveal() = throw CellCannotBeRevealedException()
	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}
}

class QuestionMarkedCell(val cell: Cell) : Cell {
	override fun reveal() = cell.reveal()
	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}
}

class BasicCell(
		private val position: Position,
		private val cellValue: CellValue,
		private var visibility: CellVisibility = HIDDEN
) : Cell {

	val adjacentPositions get() = position.adjacentPositions

	override fun reveal(): Int {
		visibility = VISIBLE
		return cellValue.value
	}

	override fun getPosition() = position

	override fun isVisible() = visibility == VISIBLE

}