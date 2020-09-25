package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.exceptions.BoardExceptionVisitor
import com.deviget.minesweeper.core.domain.exceptions.VisitableException
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class RevealCell(
		private val boardRepository: BoardRepository,
		private val boardFinisher: BoardExceptionVisitor
) {
	operator fun invoke(boardId: BoardId, coordinates: Coordinates) =
			boardRepository.find(boardId)?.let {
				try {
					it.reveal(Position(coordinates, it.edge))
				} catch (exception: VisitableException) {
					exception.accept(boardFinisher, it)
				}
			}

}