package com.deviget.minesweeper.core.domain.entities

object DefaultBoardFactory : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val cells = mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(
							x = col,
							y = row,
							edge = Edge(cols.value, rows.value)
					)
					put(position, Cell(position))
				}
			}
		}
		return Board(cells, user)
	}

}
