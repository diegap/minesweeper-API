package com.deviget.minesweeper.core.domain.entities

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.BoardFactory
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.board.BoardStatus.RUNNING
import com.deviget.minesweeper.core.domain.entities.board.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Edge
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import java.util.UUID

class DefaultBoardFactoryTest {

	private lateinit var board: Board
	private lateinit var factory: BoardFactory
	private lateinit var miner: MinerRandomizer
	private lateinit var boardIdRepository: BoardIdRepository

	private val boardId = BoardId(UUID.randomUUID())

	@Before
	fun setup() {
		miner = mock()
		boardIdRepository = mock()
		When calling miner.getMinedPositions(any(), any(), any()) itReturns
				setOf(Position(Coordinates(Pair(1, 1)), Edge(Cols(3), Rows(3))))
		When calling boardIdRepository.getNextId() itReturns boardId
	}

	@Test
	fun `create a new board`() {

		givenBoardFactory()

		whenFactoryIsInvoked(
				Rows(3), Cols(3), Mines(1), User(UserName("user1"))
		)

		thenBoardIsCreated()

	}

	private fun givenBoardFactory() {
		factory = DefaultBoardFactory(miner, boardIdRepository)
	}

	private fun whenFactoryIsInvoked(rows: Rows, cols: Cols, mines: Mines, user: User) {
		board = factory.createBoard(rows, cols, mines, user)
	}

	private fun thenBoardIsCreated() {
		board.shouldNotBeNull()
		board.id shouldBeEqualTo boardId
		board.status shouldBeEqualTo RUNNING
	}

}