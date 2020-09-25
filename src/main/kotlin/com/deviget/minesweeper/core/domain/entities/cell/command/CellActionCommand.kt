package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

interface CellActionCommand {
	fun execute(board: Board, coordinates: Coordinates): Board
}