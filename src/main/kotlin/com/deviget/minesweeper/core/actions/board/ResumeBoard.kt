package com.deviget.minesweeper.core.actions.board

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class ResumeBoard(private val boardRepository: BoardRepository) {
	operator fun invoke(board: Board) = run {
		board.resume()
		boardRepository.save(board)
		board
	}
}