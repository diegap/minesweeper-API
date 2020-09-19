package com.deviget.minesweeper.core.domain.entities

class Rows(val value: Int) {
	init {
		require(value > 0) {
			"Value for rows must be greater than 0"
		}
	}
}
