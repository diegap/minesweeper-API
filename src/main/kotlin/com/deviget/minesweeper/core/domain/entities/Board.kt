package com.deviget.minesweeper.core.domain.entities

import java.util.UUID

data class BoardId(val value: UUID)

class Board(
		val id: BoardId,
		val cellsByPosition: MutableMap<Position, Cell>,
		val user: User,
		private val minedPositions: Set<Position> = mutableSetOf(),
		val edge: Edge
) {
	fun getCell(position: Position): Cell? = cellsByPosition[position]

	fun flagCell(position: Position) {
		cellsByPosition[position]?.let {
			cellsByPosition[position] = FlaggedCell(it)
		}
	}

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