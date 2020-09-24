package com.deviget.minesweeper.infra.rest.controllers

import com.deviget.minesweeper.core.actions.RevealCell
import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.BoardId
import com.deviget.minesweeper.core.domain.entities.Coordinates
import com.deviget.minesweeper.infra.rest.representations.BoardRepresentation
import com.deviget.minesweeper.infra.rest.representations.BoardViewRepresentation
import com.deviget.minesweeper.infra.rest.representations.PositionRepresentation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class BoardController(
		private val startGame: StartGame,
		private val revealCell: RevealCell
) {

	@PostMapping("/users/{user-id}/boards")
	fun createBoard(@RequestBody boardRepresentation: BoardRepresentation): ResponseEntity<BoardViewRepresentation> {
		val board = startGame(boardRepresentation.toActionData())
		return ResponseEntity(BoardViewRepresentation(board), HttpStatus.CREATED)
	}

	@PutMapping("/users/{user-id}/boards/{board-id}")
	fun revealCell(@PathVariable("board-id") boardId: String,
				   @RequestBody position: PositionRepresentation
	): ResponseEntity<BoardViewRepresentation> {
		return revealCell(
				BoardId(UUID.fromString(boardId)),
				Coordinates(Pair(position.x.toInt(), position.y.toInt()))
		)?.let {
			ResponseEntity(BoardViewRepresentation(it), HttpStatus.OK)
		} ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}
}