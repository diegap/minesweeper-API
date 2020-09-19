package com.deviget.minesweeper.core.domain.entities

object DefaultBoardFactory : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val cells = mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(x = col, y = row)
					put(position, Cell(position, emptySet()))
				}
			}
		}
		return Board(cells, user)
	}
}
