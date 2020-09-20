package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.exceptions.GameOverException

class Board(
		val cellsByPosition: Map<Position, Cell>,
		val user: User,
		private val minedPositions: Set<Position> = mutableSetOf()
) {
	fun getCell(position: Position): Cell? = cellsByPosition[position]

	fun reveal(position: Position) {
		checkPosition(position)

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

	private fun checkPosition(position: Position) {
		if (position.isMined()) throw GameOverException()
	}

	private fun Position.isMined() = minedPositions.contains(this)
	private fun Position.isSurroundedByMines() =
			adjacentPositions.intersect(minedPositions).isNotEmpty()
}