package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class QuestionMarkCell(
		private val boardRepository: BoardRepository
) {
	operator fun invoke(boardId: BoardId, coordinates: Coordinates) =
			boardRepository.find(boardId)?.let {
				it.questionMarkCell(Position(coordinates, it.edge))
				boardRepository.save(it)
				it
			}
}