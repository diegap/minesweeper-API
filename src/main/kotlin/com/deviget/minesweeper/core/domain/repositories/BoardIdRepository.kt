package com.deviget.minesweeper.core.domain.repositories

import com.deviget.minesweeper.core.domain.entities.BoardId

interface BoardIdRepository {
	fun getNextId(): BoardId
}
