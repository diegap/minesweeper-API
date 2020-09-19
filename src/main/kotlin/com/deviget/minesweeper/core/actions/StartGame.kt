package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.Cell
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User

class StartGame {

	operator fun invoke(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val cells = mutableMapOf<Position, Cell>().apply {
			repeat(9) {
				val position = Position(x = it, y = it)
				put(position, Cell(position, emptySet()))
			}
		}

		val board = Board(cells, user)
		return board
	}

}