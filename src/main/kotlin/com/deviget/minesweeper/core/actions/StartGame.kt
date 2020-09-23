package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.Board
import com.deviget.minesweeper.core.domain.entities.BoardFactory
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.repositories.BoardRepository

class StartGame(
		private val boardRepository: BoardRepository,
		private val boardFactory: BoardFactory
) {

	operator fun invoke(actionData: ActionData): Board {
		with(actionData) {
			boardFactory.createBoard(rows, cols, mines, user)
		}.run {
			boardRepository.save(this)
			return@invoke this
		}
	}

	data class ActionData(
			val rows: Rows,
			val cols: Cols,
			val mines: Mines,
			val user: User
	)
}