package com.deviget.minesweeper.core.domain.exceptions

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardExceptionVisitor

abstract class VisitableException : Exception() {
	abstract fun accept(boardFinisher: BoardExceptionVisitor, board: Board): Board
}