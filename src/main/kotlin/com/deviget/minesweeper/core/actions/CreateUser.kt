package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.repositories.UserRepository

class CreateUser(private val userRepository: UserRepository) {
	operator fun invoke(user: User) {
		userRepository.save(user)
	}
}