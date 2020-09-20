package com.deviget.minesweeper.core.domain.entities

interface MinerRandomizer {
	fun getMinedPositions(rows: Rows, cols: Cols, mines: Mines): Set<Position>
}

class DefaultBoardFactory(
		private val miner: MinerRandomizer
) : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val minedPositions = miner.getMinedPositions(rows, cols, mines)
		return mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(
							x = col,
							y = row,
							edge = Edge(cols, rows)
					)
					put(position, Cell(
							position = position,
							cellValue = CellValue(
									position.adjacentPositions.intersect(minedPositions).size
							)
					))
				}
			}
		}.run {
			Board(this, user, minedPositions)
		}
	}

}

