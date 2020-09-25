package com.deviget.minesweeper.core.domain.repositories

import com.deviget.minesweeper.core.domain.entities.board.BoardId

interface BoardIdRepository {
	fun getNextId(): BoardId
}
