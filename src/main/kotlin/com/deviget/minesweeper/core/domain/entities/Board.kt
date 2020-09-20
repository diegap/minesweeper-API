package com.deviget.minesweeper.core.domain.entities

class Board(
		val cellsByPosition: Map<Position, Cell>,
		val user: User,
		val minedPositions: MutableSet<Position> = mutableSetOf()
) {
	fun getCell(position: Position): Cell? = cellsByPosition[position]

	fun reveal(position: Position) {
		with(cellsByPosition[position]) {
			this?.reveal()
			if (this?.adjacentPositions.orEmpty().intersect(minedPositions).isEmpty()) {
				this?.adjacentPositions.orEmpty()
						.map(::getCell)
						.forEach {
							it?.reveal()
						}
			}
		}
	}
}