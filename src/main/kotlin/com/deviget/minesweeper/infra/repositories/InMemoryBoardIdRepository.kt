package com.deviget.minesweeper.infra.repositories

import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import java.util.UUID

class InMemoryBoardIdRepository : BoardIdRepository {
	override fun getNextId() = BoardId(UUID.randomUUID())
}
