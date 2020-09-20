package com.deviget.minesweeper.core.domain.repositories

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardId

interface BoardRepository {
	fun save(board: Board)
	fun find(boardId: BoardId): Board?
}
