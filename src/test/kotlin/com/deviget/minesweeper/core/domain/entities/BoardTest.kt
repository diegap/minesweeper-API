package com.deviget.minesweeper.core.domain.entities

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Test

class BoardTest {

	private val boardFactory: BoardFactory = DefaultBoardFactory
	private lateinit var cell: Cell
	private lateinit var board: Board

	@Test
	fun `Retrieve adjacent cells at (0,0) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrivedAt(Position(0, 0, Edge(2, 2)))

		thenCellHasAdjacents(3)
	}

	@Test
	fun `Retrieve adjacent cells at (1,1) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrivedAt(Position(1, 1, Edge(2, 2)))

		thenCellHasAdjacents(8)
	}

	@Test
	fun `Retrieve adjacent cells at (2,2) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrivedAt(Position(2, 2, Edge(2, 2)))

		thenCellHasAdjacents(3)
	}

	private fun givenBoard(rows: Rows, cols: Cols, mines: Mines, user: User) {
		board = boardFactory.createBoard(rows, cols, mines, user)
	}

	private fun whenCellIsRetrivedAt(position: Position) {
		cell = board.getCell(position)!!
	}


	private fun thenCellHasAdjacents(size: Int) {
		cell.adjacentPositions shouldNotBe emptySet()
		cell.adjacentPositions.size shouldBeEqualTo size
	}

}