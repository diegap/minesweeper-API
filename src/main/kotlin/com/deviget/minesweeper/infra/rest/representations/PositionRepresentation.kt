package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.fasterxml.jackson.annotation.JsonProperty

data class PositionRepresentation(
		@JsonProperty val x: String,
		@JsonProperty val y: String
) {
	fun toDomain() = Coordinates(Pair(x.toInt(), y.toInt()))
}
