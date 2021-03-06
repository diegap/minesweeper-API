package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.board.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.cell.Cell
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Edge
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.deviget.minesweeper.core.domain.exceptions.GameOverException
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import java.util.UUID

class CellTest {

	private lateinit var miner: MinerRandomizer
	private lateinit var cell: Cell
	private lateinit var board: Board
	private lateinit var boardIdRepository: BoardIdRepository

	@Before
	fun init() {
		boardIdRepository = mock()
		whenever(boardIdRepository.getNextId()).thenReturn(BoardId(UUID.randomUUID()))
	}

	@Test
	fun `cell with no adjacent mines is revealed`() {

		givenMinerWithPositions(
				setOf(
						Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3))),
						Position(Coordinates(Pair(2, 1)), Edge(Cols(3), Rows(3)))
				)
		)
		givenMinedBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)
		givenCellWithNoAdjacentMines()

		whenCellIsRevealed()

		thenAdjacentCellsAreRevealed()

	}

	@Test(expected = GameOverException::class)
	fun `cell with mine is revealed`() {
		givenMinerWithPositions(
				setOf(Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3))))
		)
		givenMinedBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)
		givenMinedCell()

		whenCellIsRevealed()

		// exception is thrown

	}

	@Test
	fun `cell value matches with one adjacent mine`() {

		givenMinerWithPositions(
				setOf(Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3))))
		)
		givenMinedBoard(
				rows = Rows(3),
				cols = Cols(3),
				mines = Mines(1),
				user = User(UserName("user1"))
		)

		whenRetrievingCellWithAdjacentMines()

		thenCellValueIs(1)

	}

	@Test
	fun `cell value matches with three adjacent mine`() {

		givenMinerWithPositions(
				setOf(
						Position(Coordinates(Pair(0, 0)), Edge(Cols(3), Rows(3))),
						Position(Coordinates(Pair(2, 0)), Edge(Cols(3), Rows(3))),
						Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3)))
				)
		)
		givenMinedBoard(
				rows = Rows(3),
				cols = Cols(3),
				mines = Mines(3),
				user = User(UserName("user1"))
		)

		whenRetrievingCellWithAdjacentMines()

		thenCellValueIs(3)

	}

	private fun givenMinerWithPositions(positionsToMine: Set<Position>) {
		miner = mock()
		whenever(miner.getMinedPositions(any(), any(), any())).thenReturn(positionsToMine)
	}

	private fun givenMinedBoard(rows: Rows, cols: Cols, mines: Mines, user: User) {
		board = DefaultBoardFactory(miner, boardIdRepository).createBoard(rows, cols, mines, user)
	}

	private fun givenCellWithNoAdjacentMines() {
		cell = board.getCell(Position(Coordinates(Pair(0, 0)), Edge(Cols(3), Rows(3))))!!
	}

	private fun givenMinedCell() {
		cell = board.getCell(Position(Coordinates(Pair(2, 2)), Edge(Cols(3), Rows(3))))!!
	}

	private fun whenCellIsRevealed() {
		board.reveal(cell.getPosition())
	}

	private fun whenRetrievingCellWithAdjacentMines() {
		cell = board.getCell(Position(Coordinates(Pair(1, 1)), Edge(Cols(3), Rows(3))))!!
	}

	private fun thenAdjacentCellsAreRevealed() {
		cell.isVisible() shouldBeEqualTo true
		cell.getPosition().adjacentPositions.forEach {
			board.getCell(it)!!.isVisible() shouldBeEqualTo true
		}
	}

	private fun thenCellValueIs(value: Int) {
		cell.reveal() shouldBeEqualTo value
	}

}
