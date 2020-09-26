package com.deviget.minesweeper.core.domain.entities.cell.command

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates

interface CellCommand {
	fun execute(board: Board, coordinates: Coordinates): Board
}