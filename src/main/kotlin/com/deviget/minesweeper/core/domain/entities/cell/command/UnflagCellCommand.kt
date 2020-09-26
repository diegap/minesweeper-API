package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.actions.cell.UnflagCell
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

class UnflagCellCommand(private val unflagCell: UnflagCell) : CellCommand {
	override fun execute(board: Board, coordinates: Coordinates) = unflagCell(board, coordinates)
}