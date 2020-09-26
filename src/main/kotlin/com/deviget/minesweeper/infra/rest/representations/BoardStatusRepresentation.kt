package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.infra.rest.controllers.BoardStatusCommandName

data class BoardStatusRepresentation(val status: String) {
	fun toDomain(): BoardStatusCommandName {
		status.toUpperCase().apply {
			return BoardStatusCommandName.valueOf(this)
		}
	}
}
