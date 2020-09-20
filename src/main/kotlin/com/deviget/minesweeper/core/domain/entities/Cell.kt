package com.deviget.minesweeper.core.domain.entities

class Cell(val position: Position) {
	val adjacentPositions
		get() = mutableSetOf<Position?>().apply {
			add(position.upperLeft)
			add(position.upper)
			add(position.upperRight)
			add(position.left)
			add(position.right)
			add(position.lowerLeft)
			add(position.lower)
			add(position.lowerRight)
		}.filterNotNull().toSet()
}