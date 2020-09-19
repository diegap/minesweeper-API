package com.deviget.minesweeper.core.domain.entities

interface BoardFactory {
	fun createBoard(rows: Rows, cols: Cols, mines: Mines, user: User): Board
}