package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.domain.entities.board.BoardActionCommandName
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.fasterxml.jackson.annotation.JsonProperty

data class PositionRepresentation(
		@JsonProperty val x: String,
		@JsonProperty val y: String,
		@JsonProperty val command: String
) {
	fun toDomain() = Coordinates(Pair(x.toInt(), y.toInt()))

	fun toCommand() = try {
		BoardActionCommandName.valueOf(command.toUpperCase())
	} catch (exception: Exception) {
		null
	}
}
