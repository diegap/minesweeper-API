package com.deviget.minesweeper.core.domain.exceptions

import com.deviget.minesweeper.core.domain.entities.Board

abstract class VisitableException : Exception() {
	abstract fun accept(boardFinisher: BoardExceptionVisitor, board: Board): Board
}