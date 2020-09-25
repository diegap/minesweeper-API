package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.exceptions.BoardExceptionVisitor
import com.deviget.minesweeper.core.domain.exceptions.VisitableException

class RevealCell(
		private val boardFinisher: BoardExceptionVisitor
) {
	operator fun invoke(board: Board, coordinates: Coordinates) =
			try {
				board.reveal(Position(coordinates, board.edge))
			} catch (exception: VisitableException) {
				exception.accept(boardFinisher, board)
			}
}