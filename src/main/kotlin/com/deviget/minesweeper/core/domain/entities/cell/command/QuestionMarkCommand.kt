package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.actions.QuestionMarkCell
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

class QuestionMarkCommand(private val questionMarkCell: QuestionMarkCell) : CellCommand {
	override fun execute(board: Board, coordinates: Coordinates) = questionMarkCell(board, coordinates)
}