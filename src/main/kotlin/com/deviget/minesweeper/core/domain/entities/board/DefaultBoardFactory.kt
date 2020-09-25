package com.deviget.minesweeper.core.domain.entities.board

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.cell.BasicCell
import com.deviget.minesweeper.core.domain.entities.cell.Cell
import com.deviget.minesweeper.core.domain.entities.cell.CellValue
import com.deviget.minesweeper.core.domain.entities.cell.MinedCell
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.core.domain.entities.position.Edge
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository

class DefaultBoardFactory(
		private val miner: MinerRandomizer,
		private val boardIdRepository: BoardIdRepository
) : BoardFactory {

	override fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board {
		val minedPositions = miner.getMinedPositions(rows, cols, mines)
		val edge = Edge(cols, rows)
		return mutableMapOf<Position, Cell>().apply {
			repeat(rows.value) { row ->
				repeat(cols.value) { col ->
					val position = Position(Coordinates(Pair(col, row)), edge)
					put(position, getCell(position, minedPositions))
				}
			}
		}.run {
			Board(boardIdRepository.getNextId(), this, user, minedPositions, edge)
		}
	}

	private fun getCell(position: Position, minedPositions: Set<Position>) =
			with(BasicCell(position, getCellValue(position, minedPositions))) {
				when {
					minedPositions.contains(position) -> MinedCell(this)
					else -> this
				}
			}

	private fun getCellValue(position: Position, minedPositions: Set<Position>): CellValue {
		val rawValue =
				if (minedPositions.contains(position)) -1
				else position.adjacentPositions.intersect(minedPositions).size
		return CellValue(rawValue.toString())
	}

}

