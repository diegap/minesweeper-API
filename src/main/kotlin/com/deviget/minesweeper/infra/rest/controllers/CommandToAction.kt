package com.deviget.minesweeper.infra.rest.controllers

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.board.command.BoardStatusCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.CellCommand
import com.deviget.minesweeper.infra.rest.representations.BoardStatusRepresentation
import com.deviget.minesweeper.infra.rest.representations.BoardViewRepresentation
import com.deviget.minesweeper.infra.rest.representations.PositionRepresentation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CellCommandToAction(
		private val cellCommandMap: Map<CellCommandName, CellCommand>
) {
	operator fun invoke(position: PositionRepresentation, board: Board) =
			position.toCommand().run {
				cellCommandMap[this]?.execute(board, position.toDomain())?.let {
					ResponseEntity(BoardViewRepresentation(it), HttpStatus.OK)
				} ?: ResponseEntity(HttpStatus.NOT_FOUND)
			}
}

class BoardStatusCommandToAction(
		private val boardStatusCommandMap: Map<BoardStatusCommandName, BoardStatusCommand>
) {
	operator fun invoke(boardStatusRepresentation: BoardStatusRepresentation, board: Board) {
		boardStatusRepresentation.toDomain().run {
			boardStatusCommandMap[this]?.execute(board)
		}
	}
}

enum class BoardStatusCommandName {
	PAUSE,
	RESUME
}

enum class CellCommandName {
	REVEAL,
	FLAG,
	UNFLAG,
	QUESTION,
	UNQUESTION
}