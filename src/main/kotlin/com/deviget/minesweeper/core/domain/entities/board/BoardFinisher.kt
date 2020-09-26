package com.deviget.minesweeper.core.domain.entities.board

import com.deviget.minesweeper.core.domain.entities.board.BoardStatus.LOSE
import com.deviget.minesweeper.core.domain.entities.board.BoardStatus.WIN
import com.deviget.minesweeper.core.domain.exceptions.GameOverException
import com.deviget.minesweeper.core.domain.exceptions.GameOverSuccessException
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

interface BoardExceptionVisitor {
	fun visit(exception: GameOverException, board: Board): Board
	fun visit(exception: GameOverSuccessException, board: Board): Board
}

class BoardFinisher(private val boardRepository: BoardRepository) : BoardExceptionVisitor {

	override fun visit(exception: GameOverException, board: Board) = finishBoard(board, LOSE)

	override fun visit(exception: GameOverSuccessException, board: Board) = finishBoard(board, WIN)

	private fun finishBoard(board: Board, boardStatus: BoardStatus) =
			with(board) {
				this.finish(boardStatus)
				boardRepository.save(this)
				this
			}
}
