package com.deviget.minesweeper.core.domain.entities

class Cols(val value: Int) {
	init {
		require(value >= 3) {
			"Value for cols must be equal or greater than 3"
		}
	}
}
