package com.deviget.minesweeper.infra.rest.representations

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRepresentation(
		@JsonProperty val userName: String
)
