package com.deviget.minesweeper.core.domain.entities.board.command

import com.deviget.minesweeper.core.actions.board.ResumeBoard
import com.deviget.minesweeper.core.domain.entities.board.Board

class ResumeBoardCommand(private val resumeBoard: ResumeBoard) : BoardStatusCommand {
	override fun execute(board: Board) {
		resumeBoard(board)
	}
}