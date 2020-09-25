package com.deviget.minesweeper.infra.repositories

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.UserRepository

class InMemoryUserRepository(
		private val users: MutableMap<UserName, User> = mutableMapOf()
) : UserRepository {

	override fun save(user: User) {
		users[user.userName] = user
	}

	override fun get(userName: UserName) = users[userName]

}
