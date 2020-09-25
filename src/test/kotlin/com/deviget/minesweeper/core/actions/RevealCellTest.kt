package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.board.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Edge
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.deviget.minesweeper.core.domain.exceptions.BoardExceptionVisitor
import com.deviget.minesweeper.core.domain.exceptions.GameOverException
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.Verify
import org.amshove.kluent.When
import org.amshove.kluent.`should be`
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.junit.Before
import org.junit.Test
import java.util.UUID

class RevealCellTest {

	private lateinit var boardExceptionVisitor: BoardExceptionVisitor
	private lateinit var returnedBoard: Board
	private lateinit var boardIdRepository: BoardIdRepository

	private lateinit var action: RevealCell
	private lateinit var board: Board
	private lateinit var miner: MinerRandomizer

	private val boardId = BoardId(UUID.randomUUID())

	@Before
	fun init() {
		boardExceptionVisitor = mock()
	}

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
		givenRevealCellAction()

		whenRevealCellisInvoked(Coordinates(Pair(2, 2)))

		thenCellIsRevealed()

	}

	@Test
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
		givenRevealCellAction()
		givenBoardExceptionVisitor()

		whenRevealCellisInvoked(Coordinates(Pair(2, 2)))

		thenGamerOverExceptionIsHandled()

	}

	private fun givenBoardIdRepository() {
		boardIdRepository = mock()
		When calling boardIdRepository.getNextId() itReturns boardId
	}

	private fun givenMinerReturningMinesAt(positions: Set<Position>) {
		miner = mock()
		When calling miner.getMinedPositions(any(), any(), any()) itReturns positions
	}

	private fun givenBoard(rows: Rows, cols: Cols, mines: Mines, user: User, miner: MinerRandomizer) {
		board = DefaultBoardFactory(miner, boardIdRepository).createBoard(rows, cols, mines, user)
	}

	private fun givenRevealCellAction() {
		action = RevealCell(boardExceptionVisitor)
	}

	private fun givenBoardExceptionVisitor() {
		When calling boardExceptionVisitor.visit(any<GameOverException>(), any()) itReturns board
	}

	private fun whenRevealCellisInvoked(coordinates: Coordinates) {
		returnedBoard = action.invoke(board, coordinates)
	}

	private fun thenCellIsRevealed() {
		val cell = returnedBoard.getCell(Position(Coordinates(Pair(2, 2)), returnedBoard.edge))!!
		cell.isVisible() `should be` true
	}

	private fun thenGamerOverExceptionIsHandled() {
		Verify on boardExceptionVisitor that boardExceptionVisitor.visit(any<GameOverException>(), any())
	}

}
