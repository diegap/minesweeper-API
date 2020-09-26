package com.deviget.minesweeper.core.actions.cell

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class UnquestionMarkCell(private val boardRepository: BoardRepository) {
	operator fun invoke(board: Board, coordinates: Coordinates) = run {
		board.unquestionMarkCell(Position(coordinates, board.edge))
		boardRepository.save(board)
		board
	}
}