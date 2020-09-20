package com.deviget.minesweeper.core.domain.entities

class Board(val cellsByPosition: Map<Position, Cell>, val user: User) {
	fun getCell(position: Position): Cell? {
		return cellsByPosition[position]
	}
}