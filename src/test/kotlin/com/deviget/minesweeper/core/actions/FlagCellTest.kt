package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Coordinates
import com.deviget.minesweeper.core.domain.entities.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.FlaggedCell
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atMost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.`should be instance of`
import org.junit.Test
import java.util.UUID

class FlagCellTest {

	private lateinit var boardIdRepository: BoardIdRepository
	private lateinit var returnedBoard: Board
	private lateinit var action: FlagCell
	private lateinit var boardRepository: BoardRepository
	private val uuid = UUID.randomUUID()

	@Test
	fun `flag a cell`() {
		givenBoardIdRepository()
		givenBoardRepository(
				BoardId(uuid),
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)
		givenFlagCellAction()

		whenActionIsInvoked(BoardId(uuid), Coordinates(Pair(1, 1)))

		thenCellIsFlagged(BoardId(uuid), Coordinates(Pair(1, 1)))

	}

	private fun givenBoardIdRepository() {
		boardIdRepository = mock()
		whenever(boardIdRepository.getNextId()).thenReturn(BoardId(uuid))
	}

	private fun givenBoardRepository(boardId: BoardId, rows: Rows, cols: Cols, mines: Mines, user: User) {
		val board = DefaultBoardFactory(mock(), boardIdRepository).createBoard(rows, cols, mines, user)
		boardRepository = mock()
		whenever(boardRepository.find(boardId)).thenReturn(board)
	}

	private fun givenFlagCellAction() {
		action = FlagCell(boardRepository)
	}

	private fun whenActionIsInvoked(boardId: BoardId, coordinates: Coordinates) {
		returnedBoard = action.invoke(boardId, coordinates)!!
	}

	private fun thenCellIsFlagged(boardId: BoardId, coordinates: Coordinates) {
		verify(boardRepository, atMost(1)).find(boardId)
		verify(boardRepository, atMost(1)).save(any())

		val cell = returnedBoard.getCell(Position(coordinates, returnedBoard.edge))!!
		cell `should be instance of` FlaggedCell::class
	}

}