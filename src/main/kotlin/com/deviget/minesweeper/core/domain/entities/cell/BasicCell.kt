package com.deviget.minesweeper.core.domain.entities.cell

import com.deviget.minesweeper.core.domain.entities.cell.CellValueType.HIDDEN
import com.deviget.minesweeper.core.domain.entities.cell.CellVisibility.NONE
import com.deviget.minesweeper.core.domain.entities.cell.CellVisibility.VISIBLE
import com.deviget.minesweeper.core.domain.entities.position.Position

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