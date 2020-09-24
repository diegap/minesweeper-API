package com.deviget.minesweeper.core.domain.entities

interface MinerRandomizer {
	fun getMinedPositions(rows: Rows, cols: Cols, mines: Mines): Set<Position>
}