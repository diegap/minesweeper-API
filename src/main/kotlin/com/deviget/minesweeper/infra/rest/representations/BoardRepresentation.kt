package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.actions.board.StartGame
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.entities.miner.Mines
import com.deviget.minesweeper.core.domain.entities.position.Cols
import com.deviget.minesweeper.core.domain.entities.position.Rows
import com.fasterxml.jackson.annotation.JsonProperty

data class BoardResumeRepresentation(val boardIds: Set<String>)

data class BoardRepresentation(
		@JsonProperty val id: String?,
		@JsonProperty val user: UserRepresentation,
		@JsonProperty val rows: Int,
		@JsonProperty val cols: Int,
		@JsonProperty val mines: Int
) {

	fun toDomain() =
			StartGame.ActionData(
					Rows(rows),
					Cols(cols),
					Mines(mines),
					User(UserName(user.userName))
			)
}
