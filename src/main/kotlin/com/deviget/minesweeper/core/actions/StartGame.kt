package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.Cell
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class StartGame(
		private val boardRepository: BoardRepository
) {

	operator fun invoke(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val cells = mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(x = col, y = row)
					put(position, Cell(position, emptySet()))
				}
			}
		}
		val board = Board(cells, user)
		boardRepository.save(board)
		return board
	}

}