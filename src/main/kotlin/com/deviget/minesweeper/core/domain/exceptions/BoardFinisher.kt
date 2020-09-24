package com.deviget.minesweeper.core.domain.exceptions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

interface BoardExceptionVisitor {
	fun visit(exception: GameOverException, board: Board): Board
	fun visit(exception: GameOverSuccessException, board: Board): Board
}

class BoardFinisher(private val boardRepository: BoardRepository) : BoardExceptionVisitor {

	override fun visit(exception: GameOverException, board: Board) = finishBoard(board)

	override fun visit(exception: GameOverSuccessException, board: Board) = finishBoard(board)

	private fun finishBoard(board: Board) =
			with(board) {
				this.finish()
				boardRepository.save(this)
				this
			}
}
