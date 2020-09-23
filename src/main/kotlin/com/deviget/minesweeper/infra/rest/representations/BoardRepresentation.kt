package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName

data class BoardRepresentation(
		val user: UserRepresentation,
		val rows: Int,
		val cols: Int,
		val mines: Int
) {
	fun toActionData() =
			StartGame.ActionData(
					Rows(rows),
					Cols(cols),
					Mines(mines),
					User(UserName(user.userName))
			)

}
