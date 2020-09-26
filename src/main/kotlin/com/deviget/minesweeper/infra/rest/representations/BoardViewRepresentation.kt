package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.domain.entities.board.Board
import com.deviget.minesweeper.core.domain.entities.position.Position
import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.collections.Map.Entry

data class RowViewRepresentation(
		val cols: List<ColViewRepresentation>
)

data class ColViewRepresentation(
		@JsonProperty val x: String,
		@JsonProperty val y: String,
		@JsonProperty val value: String
)

data class BoardViewRepresentation(
		@JsonProperty val boardId: String,
		@JsonProperty val rows: List<RowViewRepresentation>,
		@JsonProperty val status: String,
		val elapsedTimeInSeconds: Long
) {
	constructor(board: Board) : this(
			boardId = board.id.value.toString(),
			rows = buildBoardView(board),
			status = board.status.name,
			elapsedTimeInSeconds = board.getElapsedTime
	)

	companion object {

		fun buildBoardView(board: Board): List<RowViewRepresentation> {
			return board.cellsByPosition.keys
					.groupBy { it.y }
					.map { row ->
						RowViewRepresentation(mapRows(row, board))
					}
		}

		private fun mapRows(row: Entry<Int, List<Position>>, board: Board) =
				row.value.map {
					ColViewRepresentation(
							x = it.x.toString(),
							y = it.y.toString(),
							value = mapValue(board, it)
					)
				}

		private fun mapValue(board: Board, position: Position) =
				if (board.isFinished.not()) board.cellsByPosition[position]?.getValue()?.value.orEmpty()
				else board.cellsByPosition[position]?.getHiddenValue()?.value.orEmpty()

	}

}

data class AdminBoardViewRepresentation(
		val board: BoardViewRepresentation,
		val user: UserRepresentation
) {
	constructor(board: Board) : this(
			board = BoardViewRepresentation(board),
			user = UserRepresentation(board.user)
	)
}