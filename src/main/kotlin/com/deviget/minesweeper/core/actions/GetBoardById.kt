package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class GetBoardById(private val boardRepository: BoardRepository) {
	operator fun invoke(boardId: BoardId) = boardRepository.find(boardId)
}