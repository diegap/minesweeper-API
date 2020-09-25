package com.deviget.minesweeper.core.domain.entities.board

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.board.BoardStatus.FINISHED
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
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

data class BoardId(val value: UUID)

enum class BoardStatus {
	RUNNING,
	PAUSED,
	FINISHED
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
		cellsByPosition[position]?.let {
			cellsByPosition[position] = FlaggedCell(it)
		}
	}

	fun questionMarkCell(position: Position) {
		cellsByPosition[position]?.let {
			cellsByPosition[position] = QuestionMarkedCell(it)
		}
	}

	fun reveal(position: Position): Board {
		cellsByPosition[position]?.let {
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

	fun finish() {
		lastAccess = System.nanoTime()
		status = FINISHED
	}

	@ExperimentalTime
	val getElapsedTime
		get() = elapsedTime.sum().toDuration(TimeUnit.SECONDS)

	private fun safeReveal(positions: Set<Position>) {
		if (positions.touchAnyMine()) return
		positions.forEach { position ->
			cellsByPosition[position]?.reveal()
			safeReveal(
					position.adjacentPositions
							.filter { it.isHidden() }
							.toSet()
			)
		}
	}

	private fun checkRevealedCells() {
		val (_, safePositions) = cellsByPosition.keys.partition { it in minedPositions }
		if (safePositions.size == safePositions.map { cellsByPosition[it] }.filter { it?.isVisible() == true }.size)
			throw GameOverSuccessException()
	}

	private fun Position.isHidden() = cellsByPosition[this]?.isVisible()?.not() ?: true
	private fun Set<Position>.touchAnyMine() =
			this.intersect(minedPositions).isNotEmpty()
}