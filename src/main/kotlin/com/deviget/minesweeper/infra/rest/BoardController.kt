package com.deviget.minesweeper.infra.rest

import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.infra.rest.representations.BoardRepresentation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
		private val startGame: StartGame
) {

	@PostMapping("/users/{user-id}/boards")
	fun createBoard(@RequestBody board: BoardRepresentation) {
		startGame.invoke(board.toActionData())
	}

}