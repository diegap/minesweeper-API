package com.deviget.minesweeper.infra.rest.controllers

import com.deviget.minesweeper.core.actions.FlagCell
import com.deviget.minesweeper.core.actions.GetBoardById
import com.deviget.minesweeper.core.actions.PauseBoard
import com.deviget.minesweeper.core.actions.QuestionMarkCell
import com.deviget.minesweeper.core.actions.ResumeBoard
import com.deviget.minesweeper.core.actions.RevealCell
import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.core.domain.entities.position.Coordinates
import com.deviget.minesweeper.infra.rest.representations.BoardRepresentation
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
		private val revealCell: RevealCell,
		private val getBoardById: GetBoardById,
		private val flagCell: FlagCell,
		private val questionMarkCell: QuestionMarkCell,
		private val pauseBoard: PauseBoard,
		private val resumeBoard: ResumeBoard
) {

	@PostMapping("/users/{user-id}/boards")
	fun createBoard(@RequestBody boardRepresentation: BoardRepresentation): ResponseEntity<BoardViewRepresentation> {
		val board = startGame(boardRepresentation.toActionData())
		return ResponseEntity(BoardViewRepresentation(board), CREATED)
	}

	@GetMapping("/users/{user-id}/boards/{board-id}")
	fun getBoard(
			@PathVariable("user-id") userName: String,
			@PathVariable("board-id") boardId: String
	) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				when {
					it.status.isFinished() -> ResponseEntity(NOT_FOUND)
					it.user.userName.value != userName -> ResponseEntity(NOT_FOUND)
					else -> ResponseEntity(BoardViewRepresentation(it), OK)
				}
			} ?: ResponseEntity(NOT_FOUND)

	@PutMapping("/users/{user-id}/boards/{board-id}/reveal")
	fun revealCell(@PathVariable("board-id") boardId: String,
				   @RequestBody position: PositionRepresentation
	) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				if (it.status.isFinished()) return ResponseEntity(BoardViewRepresentation(it), OK)
				revealCell(it, Coordinates(Pair(position.x.toInt(), position.y.toInt()))).run {
					ResponseEntity(BoardViewRepresentation(this), OK)
				}
			} ?: ResponseEntity(NOT_FOUND)

	@PutMapping("/users/{user-id}/boards/{board-id}/flag")
	fun flagCell(@PathVariable("board-id") boardId: String,
				 @RequestBody position: PositionRepresentation
	) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				if (it.status.isFinished()) return ResponseEntity(BoardViewRepresentation(it), OK)
				flagCell(it, Coordinates(Pair(position.x.toInt(), position.y.toInt()))).run {
					ResponseEntity(BoardViewRepresentation(this), OK)
				}
			} ?: ResponseEntity(NOT_FOUND)

	@PutMapping("/users/{user-id}/boards/{board-id}/question")
	fun questionMarkCell(@PathVariable("board-id") boardId: String,
						 @RequestBody position: PositionRepresentation
	) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				if (it.status.isFinished()) return ResponseEntity(BoardViewRepresentation(it), OK)
				questionMarkCell(it, Coordinates(Pair(position.x.toInt(), position.y.toInt()))).run {
					ResponseEntity(BoardViewRepresentation(this), OK)
				}
			} ?: ResponseEntity(NOT_FOUND)

	@PutMapping("/users/{user-id}/boards/{board-id}/pause")
	fun pause(@PathVariable("board-id") boardId: String) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				if (it.status.isFinished()) return CONFLICT
				pauseBoard(it)
				return OK
			} ?: NOT_FOUND

	@PutMapping("/users/{user-id}/boards/{board-id}/resume")
	fun resume(@PathVariable("board-id") boardId: String) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				if (it.status.isFinished()) return CONFLICT
				resumeBoard(it)
				return OK
			} ?: NOT_FOUND

}