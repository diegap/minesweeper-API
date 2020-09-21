package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository

interface MinerRandomizer {
	fun getMinedPositions(rows: Rows, cols: Cols, mines: Mines): Set<Position>
}

class DefaultBoardFactory(
		private val miner: MinerRandomizer,
		private val boardIdRepository: BoardIdRepository
) : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val minedPositions = miner.getMinedPositions(rows, cols, mines)
		val edge = Edge(cols, rows)
		return mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(Coordinates(Pair(col, row)), edge)
					put(position, getCell(position, minedPositions))
				}
			}
		}.run {
			Board(boardIdRepository.getNextId(), this, user, minedPositions, edge)
		}
	}

	private fun getCell(position: Position, minedPositions: Set<Position>) =
			with(BasicCell(position, getCellValue(position, minedPositions))) {
				when {
					minedPositions.contains(position) -> MinedCell(this)
					else -> this
				}
			}

	private fun getCellValue(position: Position, minedPositions: Set<Position>): CellValue {
		val rawValue =
				if (minedPositions.contains(position)) -1
				else position.adjacentPositions.intersect(minedPositions).size
		return CellValue(rawValue)
	}

}

