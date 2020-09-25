package com.deviget.minesweeper.core.domain.entities.cell

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

