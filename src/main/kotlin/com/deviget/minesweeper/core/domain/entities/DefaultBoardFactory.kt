package com.deviget.minesweeper.core.domain.entities

object DefaultBoardFactory : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User) =
			mutableMapOf<Position, Cell>().apply {
				repeat(rows.value) { row ->
					repeat(cols.value) { col ->
						val position = Position(
								x = col,
								y = row,
								edge = Edge(cols, rows)
						)
						put(position, Cell(position))
					}
				}
			}.run {
				Board(this, user)
			}

}
