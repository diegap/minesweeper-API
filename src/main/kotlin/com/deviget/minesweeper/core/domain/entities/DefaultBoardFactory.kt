package com.deviget.minesweeper.core.domain.entities

interface MinerRandomizer {
	fun getMinedPositions(rows: Rows, cols: Cols, mines: Mines): Set<Position>
}

class DefaultBoardFactory(
		private val miner: MinerRandomizer
) : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val minedPositions = miner.getMinedPositions(rows, cols, mines)
		val edge = Edge(cols, rows)
		return mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(
							x = col,
							y = row,
							edge = edge
					)
					put(position, Cell(
							position = position,
							cellValue = getCellValue(position, minedPositions)
					))
				}
			}
		}.run {
			Board(this, user, minedPositions, edge)
		}
	}

	private fun getCellValue(position: Position, minedPositions: Set<Position>): CellValue {
		val rawValue =
				if (minedPositions.contains(position)) -1
				else position.adjacentPositions.intersect(minedPositions).size
		return CellValue(rawValue)
	}

}

