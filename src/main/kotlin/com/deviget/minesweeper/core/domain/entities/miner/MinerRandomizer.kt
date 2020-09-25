package com.deviget.minesweeper.core.domain.entities.miner

import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.deviget.minesweeper.core.domain.entities.position.Rows

interface MinerRandomizer {
	fun getMinedPositions(rows: Rows, cols: Cols, mines: Mines): Set<Position>
}