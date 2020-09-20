package com.deviget.minesweeper.core.domain.entities

class Board(
		val cellsByPosition: Map<Position, Cell>,
		val user: User,
		private val minedPositions: MutableSet<Position> = mutableSetOf()
) {
	fun getCell(position: Position): Cell? = cellsByPosition[position]

	fun reveal(position: Position) {
		return with(cellsByPosition[position]) {
			this?.reveal()
			if (!position.isSurroundedByMines()) {
				position.adjacentPositions
						.map(::getCell)
						.forEach {
							it?.reveal()
							// TODO check for recursive call
						}
			}
		}
	}

	private fun Position.isSurroundedByMines() =
			adjacentPositions.intersect(minedPositions).isNotEmpty()
}