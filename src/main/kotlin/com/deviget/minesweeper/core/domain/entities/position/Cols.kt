package com.deviget.minesweeper.core.domain.entities.position

data class Cols(val value: Int) {
	init {
		require(value > 0) {
			"Value for cols must be greater than 0"
		}
	}
}
