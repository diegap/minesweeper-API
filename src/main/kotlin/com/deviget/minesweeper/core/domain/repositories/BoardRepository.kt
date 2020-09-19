package com.deviget.minesweeper.core.domain.repositories

import com.deviget.minesweeper.core.domain.entities.Board

interface BoardRepository {
	fun save(board: Board)
}
