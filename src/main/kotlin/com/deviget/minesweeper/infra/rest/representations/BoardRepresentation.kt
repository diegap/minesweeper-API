package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.Cols
import com.deviget.minesweeper.core.domain.entities.Mines
import com.deviget.minesweeper.core.domain.entities.Rows
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.fasterxml.jackson.annotation.JsonProperty

data class BoardRepresentation(
		@JsonProperty val user: UserRepresentation,
		@JsonProperty val rows: Int,
		@JsonProperty val cols: Int,
		@JsonProperty val mines: Int
) {
	fun toActionData() =
			StartGame.ActionData(
					Rows(rows),
					Cols(cols),
					Mines(mines),
					User(UserName(user.userName))
			)

}
