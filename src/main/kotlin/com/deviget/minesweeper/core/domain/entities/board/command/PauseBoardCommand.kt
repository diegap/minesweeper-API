package com.deviget.minesweeper.core.domain.entities.board.command

import com.deviget.minesweeper.core.actions.board.PauseBoard
import com.deviget.minesweeper.core.domain.entities.board.Board

class PauseBoardCommand(private val pauseBoard: PauseBoard) : BoardStatusCommand {
	override fun execute(board: Board) {
		pauseBoard(board)
	}
}