package com.deviget.minesweeper.infra.rest.representations

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.fasterxml.jackson.annotation.JsonProperty

data class UserRepresentation(
		@JsonProperty val userName: String
) {
	constructor(user: User) : this(
			userName = user.userName.value
	)

	fun toDomain(): User = User(UserName(userName))
}
