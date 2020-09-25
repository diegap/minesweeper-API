package com.deviget.minesweeper.core.domain.entities.cell

import com.deviget.minesweeper.core.domain.entities.cell.CellValueType.HIDDEN
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.exceptions.GameOverException

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