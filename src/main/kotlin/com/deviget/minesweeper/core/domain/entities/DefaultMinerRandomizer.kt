package com.deviget.minesweeper.core.domain.entities

import kotlin.random.Random

object DefaultMinerRandomizer : MinerRandomizer {
	override fun getMinedPositions(rows: Rows, cols: Cols, mines: Mines): Set<Position> {

		require(mines.value < rows.value * cols.value) {
			"Mines quantity must be less than ${rows.value * cols.value}"
		}

		val allPositions = mutableSetOf<Position>()
		val minedPositions = mutableSetOf<Position>()

		repeat(rows.value) { row ->
			repeat(cols.value) { col ->
				allPositions.add(Position(Coordinates(Pair(col, row)), Edge(cols, rows)))
			}
		}

		repeat(mines.value) {
			with(Random.nextInt(0, allPositions.size)) {
				minedPositions.add(allPositions.elementAt(this))
				allPositions.remove(allPositions.elementAt(this))
			}
		}

		return minedPositions
	}
}
