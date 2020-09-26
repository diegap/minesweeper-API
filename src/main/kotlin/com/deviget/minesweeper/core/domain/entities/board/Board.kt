package com.deviget.minesweeper.core.domain.entities.board

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.board.BoardStatus.PAUSED
import com.deviget.minesweeper.core.domain.entities.board.BoardStatus.RUNNING
import com.deviget.minesweeper.core.domain.entities.cell.Cell
import com.deviget.minesweeper.core.domain.entities.cell.FlaggedCell
import com.deviget.minesweeper.core.domain.entities.cell.QuestionMarkedCell
import com.deviget.minesweeper.core.domain.entities.position.Edge
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.exceptions.GameOverSuccessException
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

data class BoardId(val value: UUID)

enum class BoardStatus {
	RUNNING,
	PAUSED,
	WIN,
	LOSE;

	fun isFinished(): Boolean = this == WIN || this == LOSE
}

class Board(
		val id: BoardId,
		val cellsByPosition: MutableMap<Position, Cell>,
		val user: User,
		private val minedPositions: Set<Position> = mutableSetOf(),
		val edge: Edge,
		var lastAccess: Long = System.nanoTime(),
		private val elapsedTime: MutableSet<Long> = mutableSetOf(),
		var status: BoardStatus = RUNNING
) {
	fun getCell(position: Position): Cell? = cellsByPosition[position]

	fun flagCell(position: Position) {
		getCell(position)?.let {
			cellsByPosition[position] = FlaggedCell(it)
		}
	}

	fun unflagCell(position: Position) {
		getCell(position)?.let {
			if (it is FlaggedCell) cellsByPosition[position] = it.unMark()
		}
	}

	fun questionMarkCell(position: Position) {
		getCell(position)?.let {
			cellsByPosition[position] = QuestionMarkedCell(it)
		}
	}

	fun unquestionMarkCell(position: Position) {
		getCell(position)?.let {
			if (it is QuestionMarkedCell) cellsByPosition[position] = it.unMark()
		}
	}

	fun reveal(position: Position): Board {
		getCell(position)?.let {
			it.reveal()
			safeReveal(position.adjacentPositions)
			checkRevealedCells()
		}
		return this
	}

	fun pause() {
		with(System.nanoTime()) {
			elapsedTime.add((this.minus(lastAccess)))
			lastAccess = this
			status = PAUSED
		}
	}

	fun resume() {
		lastAccess = System.nanoTime()
		status = RUNNING
	}

	fun finish(boardStatus: BoardStatus) {
		with(System.nanoTime()) {
			elapsedTime.add((this.minus(lastAccess)))
			lastAccess = this
			status = boardStatus
		}
	}

	val isFinished get() = status.isFinished()

	val getElapsedTime: Long
		get() = SECONDS.convert(elapsedTime.sum(), TimeUnit.NANOSECONDS)

	private fun safeReveal(positions: Set<Position>) {
		if (positions.touchAnyMine()) return
		positions.forEach { position ->
			getCell(position)?.reveal()
			safeReveal(
					position.adjacentPositions
							.filter { it.isHidden() }
							.toSet()
			)
		}
	}

	private fun checkRevealedCells() {
		val (_, safePositions) = cellsByPosition.keys.partition { it in minedPositions }
		if (safePositions.size == safePositions.map { getCell(it) }.filter { it?.isVisible() == true }.size)
			throw GameOverSuccessException()
	}

	private fun Position.isHidden() = getCell(this)?.isVisible()?.not() ?: true

	private fun Set<Position>.touchAnyMine() =
			this.intersect(minedPositions).isNotEmpty()

}