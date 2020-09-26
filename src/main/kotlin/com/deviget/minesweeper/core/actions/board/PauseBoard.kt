package com.deviget.minesweeper.core.actions.board

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class PauseBoard(private val boardRepository: BoardRepository) {
	operator fun invoke(board: Board) = run {
		board.pause()
		boardRepository.save(board)
		board
	}
}