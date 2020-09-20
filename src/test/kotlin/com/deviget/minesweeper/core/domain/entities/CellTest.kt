package com.deviget.minesweeper.core.domain.entities

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class CellTest {

	private lateinit var cell: Cell
	private lateinit var board: Board

	@Test
	fun `cell with no adjacent mines is revealed`() {

		givenMinedBoard(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1")),
				Position(2, 2, Edge(Cols(3), Rows(3)))
		)
		givenCellWithNoAdjacentMines()

		whenCellIsRevealed()

		thenAdjacentCellsAreRevealed()

	}

	private fun givenMinedBoard(rows: Rows, cols: Cols, mines: Mines, user: User, minedPosition: Position) {
		board = DefaultBoardFactory.createBoard(rows, cols, mines, user)
		board.minedPositions.add(minedPosition)
	}

	private fun givenCellWithNoAdjacentMines() {
		cell = board.getCell(Position(0, 0, Edge(Cols(3), Rows(3))))!!
	}

	private fun whenCellIsRevealed() {
		board.reveal(cell.position)
	}

	private fun thenAdjacentCellsAreRevealed() {
		cell.isVisible() shouldBeEqualTo true
		cell.adjacentPositions.forEach {
			board.getCell(it)!!.isVisible() shouldBeEqualTo true
		}
	}

}