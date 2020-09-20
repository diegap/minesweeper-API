package com.deviget.minesweeper.core.domain.entities

import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class BoardFactoryTest {

	private lateinit var board: Board
	private lateinit var boardFactory: BoardFactory

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
		val miner: MinerRandomizer = mock()
		boardFactory = DefaultBoardFactory(miner)
	}

	private fun whenFactoryIsInvokedWith(rows: Rows, cols: Cols, mines: Mines, user: User) {
		board = boardFactory.createBoard(rows, cols, mines, user)
	}

	private fun thenBoardIsCreatedWithCellsSize(cellsSize: Int) {
		board.cellsByPosition.size shouldBeEqualTo cellsSize
	}


}