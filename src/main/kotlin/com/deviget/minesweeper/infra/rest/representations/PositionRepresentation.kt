package com.deviget.minesweeper.infra.rest.representations

import com.fasterxml.jackson.annotation.JsonProperty

data class PositionRepresentation(
		@JsonProperty val x: String,
		@JsonProperty val y: String,
		@JsonProperty val value: String
)
