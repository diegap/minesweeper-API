package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.infra.rest.controllers.CellCommandName
import com.fasterxml.jackson.annotation.JsonProperty

data class PositionRepresentation(
		@JsonProperty val x: String,
		@JsonProperty val y: String,
		@JsonProperty val command: String
) {
	fun toDomain() = Coordinates(Pair(x.toInt(), y.toInt()))

	fun toCommand() = CellCommandName.valueOf(command.toUpperCase())

}
