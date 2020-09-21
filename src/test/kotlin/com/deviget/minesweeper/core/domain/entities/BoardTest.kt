package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test
import java.util.UUID

class BoardTest {

	private lateinit var boardFactory: BoardFactory
	private lateinit var cell: Cell
	private lateinit var board: Board

	@Before
	fun init() {
		val boardIdRepository: BoardIdRepository = mock()
		whenever(boardIdRepository.getNextId()).thenReturn(BoardId(UUID.randomUUID()))
		boardFactory = DefaultBoardFactory(mock(), boardIdRepository)
	}

	@Test
	fun `Retrieve adjacent cells at (0,0) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrievedAt(Position(Coordinates(Pair(0, 0)), Edge(Cols(3), Rows(3))))

		thenCellHasAdjacentPositions(3)
	}

	@Test
	fun `Retrieve adjacent cells at (1,1) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrievedAt(Position(Coordinates(Pair(1, 1)), Edge(Cols(3), Rows(3))))

		thenCellHasAdjacentPositions(8)
	}

	@Test
	fun `Retrieve adjacent cells at (2,2) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrievedAt(Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3))))

		thenCellHasAdjacentPositions(3)
	}

	@Test
	fun `Retrieve adjacent cells at (0,2) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrievedAt(Position(Coordinates(Pair(0, 2)), Edge(Cols(3), Rows(3))))

		thenCellHasAdjacentPositions(3)
	}

	@Test
	fun `Retrieve adjacent cells at (1,0) on a 3x3 board`() {
		givenBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		whenCellIsRetrievedAt(Position(Coordinates(Pair(1, 0)), Edge(Cols(3), Rows(3))))

		thenCellHasAdjacentPositions(5)
	}

	private fun givenBoard(rows: Rows, cols: Cols, mines: Mines, user: User) {
		board = boardFactory.createBoard(rows, cols, mines, user)
	}

	private fun whenCellIsRetrievedAt(position: Position) {
		cell = board.getCell(position)!!
	}


	private fun thenCellHasAdjacentPositions(size: Int) {
		cell.getPosition().adjacentPositions shouldNotBe emptySet()
		cell.getPosition().adjacentPositions.size shouldBeEqualTo size
	}

}