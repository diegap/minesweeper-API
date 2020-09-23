package com.deviget.minesweeper.infra.repositories

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class InMemoryBoardRepository : BoardRepository {

	override fun save(board: Board) {
		TODO("Not yet implemented")
	}

	override fun find(boardId: BoardId): Board? {
		TODO("Not yet implemented")
	}
}