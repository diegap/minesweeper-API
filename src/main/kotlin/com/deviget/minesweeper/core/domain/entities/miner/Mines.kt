package com.deviget.minesweeper.core.domain.entities.miner

class Mines(val value: Int) {
	init {
		require(value > 0) {
			"Value for mines must be greater than 0"
		}
	}
}
