package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Coordinates
import com.deviget.minesweeper.core.domain.entities.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.`should be`
import org.junit.Test
import java.util.UUID

class RevealCellTest {

	private val boardId = BoardId(UUID.randomUUID())

	private lateinit var returnedBoard: Board
	private lateinit var boardRepository: BoardRepository
	private lateinit var action: RevealCell
	private lateinit var board: Board

	@Test
	fun `retrieve cell by position`() {

		givenBoard(boardId, User(UserName("user1")))
		givenBoardRepository(boardId)
		givenRevealCellAction()

		whenRevealCellisInvoked(
				boardId,
				Coordinates(Pair(2, 2)))

		thenBoardIsRetrieved()
		thenCellIsRevealed()

	}

	private fun givenBoard(boardId: BoardId, user: User) {
		board = DefaultBoardFactory(mock()).createBoard(Rows(3), Cols(3), Mines(1), user)
	}

	private fun givenBoardRepository(boardId: BoardId) {
		boardRepository = mock()
		whenever(boardRepository.find(boardId)).thenReturn(board)
	}

	private fun givenRevealCellAction() {
		action = RevealCell(boardRepository)
	}

	private fun whenRevealCellisInvoked(boardId: BoardId, coordinates: Coordinates) {
		returnedBoard = action.invoke(boardId, coordinates)!!
	}

	private fun thenBoardIsRetrieved() {
		verify(boardRepository, times(1)).find(any())
	}

	private fun thenCellIsRevealed() {
		val cell = returnedBoard.getCell(Position(2, 2, returnedBoard.edge))!!
		cell.isVisible() `should be` true
	}

}