package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.actions.RevealCell
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

class RevealActionCommand(private val revealCell: RevealCell) : CellActionCommand {
	override fun execute(board: Board, coordinates: Coordinates) = revealCell(board, coordinates)
}