package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.domain.entities.board.BoardStatusCommandName

data class BoardStatusRepresentation(val status: String) {
	fun toDomain(): BoardStatusCommandName? {
		status.toUpperCase().apply {
			return try {
				BoardStatusCommandName.valueOf(this)
			} catch (exception: Exception) {
				null
			}
		}
	}
}