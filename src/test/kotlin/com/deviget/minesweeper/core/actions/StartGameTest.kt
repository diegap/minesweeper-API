package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardFactory
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.nhaarman.mockitokotlin2.atMost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Test

class StartGameTest {

	private val boardRepository: BoardRepository = mock()
	private lateinit var board: Board
	private lateinit var rows: Rows
	private lateinit var cols: Cols
	private lateinit var mines: Mines
	private lateinit var user: User

	private lateinit var startGame: StartGame

	@Test
	fun `Create a 3x3 board`() {

		givenInputParams(3, 3, 1, "user1")
		givenStartGameAction()

		whenActionIsInvoked()

		thenBoardIsCreatedWithCellsSize(9)
	}

	@Test
	fun `Create a 2x3 board`() {

		givenInputParams(2, 3, 1, "user1")
		givenStartGameAction()

		whenActionIsInvoked()

		thenBoardIsCreatedWithCellsSize(6)
	}

	@Test
	fun `Create a 3x2 board`() {

		givenInputParams(3, 2, 1, "user1")
		givenStartGameAction()

		whenActionIsInvoked()

		thenBoardIsCreatedWithCellsSize(6)
	}

	@Test
	fun `Create a 4x4 board`() {

		givenInputParams(4, 4, 1, "user1")
		givenStartGameAction()

		whenActionIsInvoked()

		thenBoardIsCreatedWithCellsSize(16)
	}

	private fun givenInputParams(rowSize: Int, colSize: Int, mineSize: Int, userName: String) {
		rows = Rows(rowSize)
		cols = Cols(colSize)
		mines = Mines(mineSize)
		user = User(UserName(userName))
	}

	private fun givenStartGameAction() {
		startGame = StartGame(boardRepository, BoardFactory)
	}

	private fun whenActionIsInvoked() {
		board = startGame(rows, cols, mines, user)
	}

	private fun thenBoardIsCreatedWithCellsSize(cellsSize: Int) {
		board shouldNotBe null
		board.cellsByPosition.size shouldBeEqualTo cellsSize

		verify(boardRepository, atMost(1)).save(board)
	}

}