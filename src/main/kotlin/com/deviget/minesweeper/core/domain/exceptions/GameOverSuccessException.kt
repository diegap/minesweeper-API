package com.deviget.minesweeper.core.domain.exceptions

import com.deviget.minesweeper.core.domain.entities.Board

class GameOverSuccessException : VisitableException() {
	override fun accept(boardFinisher: BoardExceptionVisitor, board: Board) =
			boardFinisher.visit(this, board)
}
