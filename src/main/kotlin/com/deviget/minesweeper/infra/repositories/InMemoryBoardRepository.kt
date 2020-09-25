package com.deviget.minesweeper.infra.repositories

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class InMemoryBoardRepository(
		private val boards: MutableMap<BoardId, Board> = mutableMapOf()
) : BoardRepository {

	override fun save(board: Board) {
		boards[board.id] = board
	}

	override fun find(boardId: BoardId) = boards[boardId]

	override fun findAll(): Set<BoardId> = boards.keys
}