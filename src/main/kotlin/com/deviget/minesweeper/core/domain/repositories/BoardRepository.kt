package com.deviget.minesweeper.core.domain.repositories

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardId

interface BoardRepository {
	fun save(board: Board)
	fun find(boardId: BoardId): Board?
	fun findAll(): Set<BoardId>
}
