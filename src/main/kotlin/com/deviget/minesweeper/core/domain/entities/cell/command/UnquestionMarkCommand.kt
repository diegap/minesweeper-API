package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.actions.cell.UnquestionMarkCell
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

class UnquestionMarkCommand(private val unquestionMarkCell: UnquestionMarkCell) : CellCommand {
	override fun execute(board: Board, coordinates: Coordinates) = unquestionMarkCell(board, coordinates)
}
