package com.deviget.minesweeper.core.domain.repositories

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName

interface UserRepository {
	fun save(user: User)
	fun get(userName: UserName): User?
}