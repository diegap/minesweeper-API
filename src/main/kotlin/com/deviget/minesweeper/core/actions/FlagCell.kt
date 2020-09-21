package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.entities.Coordinates
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class FlagCell(
		private val boardRepository: BoardRepository
) {
	operator fun invoke(boardId: BoardId, coordinates: Coordinates) =
			boardRepository.find(boardId)?.let {
				it.flagCell(Position(coordinates, it.edge))
				boardRepository.save(it)
				it
			}
}