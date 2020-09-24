package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.Verify
import org.amshove.kluent.When
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test
import java.util.UUID

class GetBoardByIdTest {

	private lateinit var getBoardById: GetBoardById
	private lateinit var boardRepository: BoardRepository


	@Test
	fun `attempt to retrieve a board`() {
		givenBoardRepository()
		givenGetBoardByIdAction()

		whenActionIsInvoked(BoardId(UUID.randomUUID()))

		thenBoardRetrievalIsAttempted()
	}

	private fun givenBoardRepository() {
		boardRepository = mock()
		When calling boardRepository.find(any()) itReturns null
	}

	private fun givenGetBoardByIdAction() {
		getBoardById = GetBoardById(boardRepository)
	}

	private fun whenActionIsInvoked(boardId: BoardId) {
		getBoardById(boardId)
	}

	private fun thenBoardRetrievalIsAttempted() {
		Verify on boardRepository that boardRepository.find(any()) was called
	}

}
