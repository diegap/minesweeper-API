package com.deviget.minesweeper.infra.rest.controllers.admin

import com.deviget.minesweeper.core.actions.board.GetBoardById
import com.deviget.minesweeper.core.actions.board.GetBoards
import com.deviget.minesweeper.core.domain.entities.board.BoardId
import com.deviget.minesweeper.infra.rest.representations.AdminBoardViewRepresentation
import com.deviget.minesweeper.infra.rest.representations.BoardResumeRepresentation
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.websocket.server.PathParam

@RestController
class BoardAdminController(
		private val getBoards: GetBoards,
		private val getBoardById: GetBoardById
) {

	@GetMapping("/admin/boards")
	fun retrieveBoards() =
			getBoards()
					.map(BoardId::value)
					.map(UUID::toString)
					.toSet()
					.run {
						ResponseEntity(BoardResumeRepresentation(boardIds = this), OK)
					}

	@GetMapping("/admin/boards/{board-id}")
	fun retrieveBoard(@PathParam("board-id") boardId: String) =
			getBoardById(BoardId(UUID.fromString(boardId)))?.let {
				ResponseEntity(AdminBoardViewRepresentation(it), OK)
			} ?: ResponseEntity(NOT_FOUND)
}