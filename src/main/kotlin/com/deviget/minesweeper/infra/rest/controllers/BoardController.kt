package com.deviget.minesweeper.infra.rest.controllers

import com.deviget.minesweeper.core.actions.board.GetBoardById
import com.deviget.minesweeper.core.actions.board.StartGame
import com.deviget.minesweeper.core.actions.user.GetUser
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.infra.rest.representations.BoardRepresentation
import com.deviget.minesweeper.infra.rest.representations.BoardStatusRepresentation
import com.deviget.minesweeper.infra.rest.representations.BoardViewRepresentation
import com.deviget.minesweeper.infra.rest.representations.PositionRepresentation
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class BoardController(
		private val startGame: StartGame,
		private val getBoardById: GetBoardById,
		private val getUser: GetUser,
		private val statusCommand: BoardStatusCommandToAction,
		private val cellCommand: CellCommandToAction
) {

	@PostMapping("/users/{user-id}/boards")
	fun createBoard(
			@RequestBody boardRepresentation: BoardRepresentation,
			@PathVariable("user-id") userName: String
	) =
			getUser(UserName(userName))?.let {
				with(startGame(boardRepresentation.toDomain())) {
					ResponseEntity(BoardViewRepresentation(this), CREATED)
				}
			} ?: ResponseEntity(NOT_FOUND)

	@GetMapping("/users/{user-id}/boards/{board-id}")
	fun getBoard(
			@PathVariable("user-id") userName: String,
			@PathVariable("board-id") boardId: String
	) =
			getBoardById(boardId)?.let {
				if (it.user.userName.value != userName) ResponseEntity(NOT_FOUND)
				else ResponseEntity(BoardViewRepresentation(it), OK)
			} ?: ResponseEntity(NOT_FOUND)


	@PutMapping("/users/{user-id}/boards/{board-id}/cells")
	fun actuate(@PathVariable("board-id") boardId: String,
				@RequestBody position: PositionRepresentation
	) =
			getBoardById(boardId)?.let { it ->
				if (it.status.isFinished()) return ResponseEntity(BoardViewRepresentation(it), OK)
				cellCommand(position, it)
			} ?: ResponseEntity(NOT_FOUND)

	@PutMapping("/users/{user-id}/boards/{board-id}/status")
	fun updateStatus(
			@PathVariable("board-id") boardId: String,
			@RequestBody boardStatusRepresentation: BoardStatusRepresentation
	) =
			getBoardById(boardId)?.let {
				if (it.status.isFinished()) return CONFLICT
				statusCommand(boardStatusRepresentation, it)
				OK

			} ?: NOT_FOUND

	private fun getBoardById(boardId: String) = getBoardById(BoardId(UUID.fromString(boardId)))
}