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
		cellsByPosition[position]?.let {
			it.reveal()
			safeReveal(position.adjacentPositions)
		}
	}

	private fun safeReveal(positions: Set<Position>) {
		if (positions.touchAnyMine()) return
		positions.forEach {
			cellsByPosition[it]?.reveal()
			it.marked = true
			safeReveal(
					it.adjacentPositions.filter(Position::marked).toSet()
			)
		}
	}

	private fun Set<Position>.touchAnyMine() =
			this.intersect(minedPositions).isNotEmpty()
}