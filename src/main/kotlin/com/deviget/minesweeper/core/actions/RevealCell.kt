package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.entities.Coordinates
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class RevealCell(
		private val boardRepository: BoardRepository
) {
	operator fun invoke(boardId: BoardId, coordinates: Coordinates): Board? {
		return boardRepository.find(boardId)?.let {
			it.reveal(Position(coordinates.rawValue.first, coordinates.rawValue.second, it.edge))
			return@invoke it
		}
	}
}