package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.CellValueType.FLAG
import com.deviget.minesweeper.core.domain.entities.CellValueType.HIDDEN
import com.deviget.minesweeper.core.domain.entities.CellValueType.QUESTION
import com.deviget.minesweeper.core.domain.entities.CellVisibility.NONE
import com.deviget.minesweeper.core.domain.entities.CellVisibility.VISIBLE
import com.deviget.minesweeper.core.domain.exceptions.CellCannotBeRevealedException
import com.deviget.minesweeper.core.domain.exceptions.GameOverException

enum class CellVisibility {
	NONE,
	VISIBLE
}

enum class CellValueType {
	HIDDEN,
	FLAG,
	QUESTION
}

data class CellValue(val value: String)

interface Cell {
	fun reveal(): Int
	fun getPosition(): Position
	fun isVisible(): Boolean
	fun getValue(): CellValue
}

class MinedCell(private val cell: Cell) : Cell {
	override fun reveal() = throw GameOverException()

	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}

	override fun getValue() = CellValue(HIDDEN.name)

}

class FlaggedCell(private val cell: Cell) : Cell {
	override fun reveal() = throw CellCannotBeRevealedException()
	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}

	override fun getValue(): CellValue {
		return CellValue(FLAG.name)
	}
}

class QuestionMarkedCell(private val cell: Cell) : Cell {
	override fun reveal() = cell.reveal()
	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}

	override fun getValue(): CellValue {
		return CellValue(QUESTION.name)
	}
}

class BasicCell(
		private val position: Position,
		private val cellValue: CellValue,
		private var visibility: CellVisibility = NONE
) : Cell {

	override fun reveal(): Int {
		visibility = VISIBLE
		return cellValue.value.toInt()
	}

	override fun getPosition() = position

	override fun isVisible() = visibility == VISIBLE

	override fun getValue(): CellValue {
		return if (isVisible()) cellValue
		else CellValue(HIDDEN.name)
	}

}