package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.actions.FlagCell
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

class FlagActionCommand(private val flagCell: FlagCell) : CellActionCommand {
	override fun execute(board: Board, coordinates: Coordinates) = flagCell(board, coordinates)
}