package com.deviget.minesweeper.core.domain.entities.cell

import com.deviget.minesweeper.core.domain.entities.cell.CellValueType.FLAG
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.exceptions.CellCannotBeRevealedException

class FlaggedCell(private val cell: Cell) : Cell, UnMarkableCell {
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

	override fun getHiddenValue() = cell.getHiddenValue()

	override fun unMark() = cell
}