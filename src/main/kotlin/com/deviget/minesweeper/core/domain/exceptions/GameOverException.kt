package com.deviget.minesweeper.core.domain.exceptions

import com.deviget.minesweeper.core.domain.entities.Board

class GameOverException : VisitableException() {
	override fun accept(boardFinisher: BoardExceptionVisitor, board: Board): Board {
		return boardFinisher.visit(this, board)
	}
}
