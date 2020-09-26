package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.actions.cell.RevealCell
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

class RevealCommand(private val revealCell: RevealCell) : CellCommand {
	override fun execute(board: Board, coordinates: Coordinates) = revealCell(board, coordinates)
}