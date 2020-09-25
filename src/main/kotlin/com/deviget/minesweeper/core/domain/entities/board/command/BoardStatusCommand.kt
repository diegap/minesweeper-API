package com.deviget.minesweeper.core.domain.entities.board.command

import com.deviget.minesweeper.core.domain.entities.board.Board

interface BoardStatusCommand {
	fun execute(board: Board)
}