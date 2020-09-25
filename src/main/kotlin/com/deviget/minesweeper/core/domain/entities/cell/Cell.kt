package com.deviget.minesweeper.core.domain.entities.cell

import com.deviget.minesweeper.core.domain.entities.position.Position

interface Cell {
	fun reveal(): Int
	fun getPosition(): Position
	fun isVisible(): Boolean
	fun getValue(): CellValue
}