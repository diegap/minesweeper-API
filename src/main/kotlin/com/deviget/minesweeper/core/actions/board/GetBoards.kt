package com.deviget.minesweeper.core.actions.board

import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class GetBoards(private val boardRepository: BoardRepository) {
	operator fun invoke() = boardRepository.findAll()
}