package com.deviget.minesweeper.core.domain.entities

class Rows(val value: Int) {
	init {
		require(value >= 3) {
			"Value for rows must be equal or greater than 3"
		}
	}
}
