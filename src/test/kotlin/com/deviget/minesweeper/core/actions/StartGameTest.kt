package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.actions.board.StartGame
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.entities.board.BoardFactory
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atMost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class StartGameTest {

	private lateinit var boardFactory: BoardFactory
	private lateinit var boardRepository: BoardRepository
	private lateinit var startGame: StartGame

	@Test
	fun `start a new game`() {
		givenBoardFactory()
		givenBoardRepository()
		givenStartGameAction()

		whenActionIsInvokedWith(
				Rows(3),
				Cols(3),
				Mines(1),
				User(UserName("user1"))
		)

		thenBoardIsCreated()
	}

	private fun givenBoardFactory() {
		boardFactory = mock()
	}

	private fun givenBoardRepository() {
		boardRepository = mock()
	}

	private fun givenStartGameAction() {
		startGame = StartGame(boardRepository, boardFactory)
	}

	private fun whenActionIsInvokedWith(rows: Rows, cols: Cols, mines: Mines, user: User) {
		startGame(StartGame.ActionData(rows, cols, mines, user))
	}

	private fun thenBoardIsCreated() {
		verify(boardFactory, atMost(1)).createBoard(any(), any(), any(), any())
		verify(boardRepository, atMost(1)).save(any())
	}

}