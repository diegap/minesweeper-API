package com.deviget.minesweeper.core.domain.entities

class Board(val cellsByPosition: Map<Position, Cell>, val user: User)

class Cell(val position: Position, val adjacents: Set<Position>)

data class Position(val x: Int, val y: Int) {
	init {
		require(x >= 0) {
			"x must be equal or greater than 0 "
		}
		require(y >= 0) {
			"y must be equal or greater than 0 "
		}
	}
}
