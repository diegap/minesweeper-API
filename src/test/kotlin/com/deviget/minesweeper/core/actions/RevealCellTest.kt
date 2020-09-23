package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Coordinates
import com.deviget.minesweeper.core.domain.entities.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.Edge
import com.deviget.minesweeper.core.domain.entities.MinerRandomizer
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Position
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.exceptions.GameOverException
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
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

	private lateinit var returnedBoard: Board
	private lateinit var boardRepository: BoardRepository
	private lateinit var boardIdRepository: BoardIdRepository

	private lateinit var action: RevealCell
	private lateinit var board: Board
	private lateinit var miner: MinerRandomizer

	private val boardId = BoardId(UUID.randomUUID())

	@Test
	fun `reveal basic cell by position`() {

		givenMinerReturningMinesAt(
				setOf(
						Position(Coordinates(Pair(0, 0)), Edge(Cols(3), Rows(3))),
						Position(Coordinates(Pair(1, 1)), Edge(Cols(3), Rows(3)))
				)
		)
		givenBoardIdRepository()
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1")),
				miner
		)
		givenBoardRepository(boardId)
		givenRevealCellAction()

		whenRevealCellisInvoked(
				boardId,
				Coordinates(Pair(2, 2)))

		thenBoardIsRetrieved()
		thenCellIsRevealed()

	}

	@Test(expected = GameOverException::class)
	fun `reveal mined cell by position`() {

		givenMinerReturningMinesAt(
				setOf(Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3))))
		)
		givenBoardIdRepository()
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1")),
				miner
		)
		givenBoardRepository(boardId)
		givenRevealCellAction()

		whenRevealCellisInvoked(
				boardId,
				Coordinates(Pair(2, 2)))

		thenBoardIsRetrieved()

	}

	private fun givenMinerReturningMinesAt(positions: Set<Position>) {
		miner = mock()
		whenever(miner.getMinedPositions(any(), any(), any())).thenReturn(positions)
	}

	private fun givenBoard(rows: Rows, cols: Cols, mines: Mines, user: User, miner: MinerRandomizer) {
		board = DefaultBoardFactory(miner, boardIdRepository).createBoard(rows, cols, mines, user)
	}

	private fun givenBoardIdRepository() {
		boardIdRepository = mock()
		whenever(boardIdRepository.getNextId()).thenReturn(boardId)
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
		val cell = returnedBoard.getCell(Position(Coordinates(Pair(2, 2)), returnedBoard.edge))!!
		cell.isVisible() `should be` true
	}

}