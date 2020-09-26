package com.deviget.minesweeper.core.domain.entities.cell

import com.deviget.minesweeper.core.domain.entities.cell.CellValueType.QUESTION
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.exceptions.CellCannotBeRevealedException

class QuestionMarkedCell(val cell: Cell) : Cell {
	override fun reveal() = throw CellCannotBeRevealedException()

	override fun getPosition(): Position {
		return cell.getPosition()
	}

	override fun isVisible(): Boolean {
		return cell.isVisible()
	}

	override fun getValue(): CellValue {
		return CellValue(QUESTION.name)
	}

	override fun getHiddenValue() = cell.getHiddenValue()
}