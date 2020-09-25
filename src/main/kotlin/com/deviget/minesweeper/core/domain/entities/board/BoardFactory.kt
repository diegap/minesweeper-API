package com.deviget.minesweeper.core.domain.entities.board

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Rows

interface BoardFactory {
	fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board
}