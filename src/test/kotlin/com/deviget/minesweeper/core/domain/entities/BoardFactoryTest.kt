package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardFactory
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.board.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.util.UUID

class BoardFactoryTest {

	private lateinit var miner: MinerRandomizer
	private lateinit var board: Board
	private lateinit var boardFactory: BoardFactory
	private lateinit var boardIdRepository: BoardIdRepository

	@Before
	fun init() {
		miner = mock()
		boardIdRepository = mock()
		whenever(boardIdRepository.getNextId()).thenReturn(BoardId(UUID.randomUUID()))
	}

	@Test
	fun `Create a 3x3 board`() {
		givenBoardFactory()

		whenFactoryIsInvokedWith(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		thenBoardIsCreatedWithCellsSize(9)
	}

	@Test
	fun `Create a 2x3 board`() {
		givenBoardFactory()

		whenFactoryIsInvokedWith(
				Rows(2),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		thenBoardIsCreatedWithCellsSize(6)
	}

	@Test
	fun `Create a 3x2 board`() {
		givenBoardFactory()

		whenFactoryIsInvokedWith(
				Rows(3),
				Cols(2),
				Mines(1),
				User(UserName("user1"))
		)

		thenBoardIsCreatedWithCellsSize(6)
	}

	@Test
	fun `Create a 4x4 board`() {
		givenBoardFactory()

		whenFactoryIsInvokedWith(
				Rows(4),
				Cols(4),
				Mines(2),
				User(UserName("user1"))
		)

		thenBoardIsCreatedWithCellsSize(16)
	}

	private fun givenBoardFactory() {
		boardFactory = DefaultBoardFactory(miner, boardIdRepository)
	}

	private fun whenFactoryIsInvokedWith(rows: Rows, cols: Cols, mines: Mines, user: User) {
		board = boardFactory.createBoard(rows, cols, mines, user)
	}

	private fun thenBoardIsCreatedWithCellsSize(cellsSize: Int) {
		board.cellsByPosition.size shouldBeEqualTo cellsSize
	}

}