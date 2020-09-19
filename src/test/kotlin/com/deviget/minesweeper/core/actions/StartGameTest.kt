package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Test

class StartGameTest {

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

		thenBoardIsCreated()
	}

	private fun givenInputParams(rowSize: Int, colSize: Int, mineSize: Int, userName: String) {
		rows = Rows(rowSize)
		cols = Cols(colSize)
		mines = Mines(mineSize)
		user = User(UserName(userName))
	}

	private fun givenStartGameAction() {
		startGame = StartGame()
	}

	private fun whenActionIsInvoked() {
		board = startGame(rows, cols, mines, user)
	}

	private fun thenBoardIsCreated() {
		board shouldNotBe null
		board.cells.size shouldBeEqualTo 9
	}

}