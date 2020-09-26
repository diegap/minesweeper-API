package com.deviget.minesweeper.core.actions.user

import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.UserRepository

class GetUser(private val userRepository: UserRepository) {
	operator fun invoke(userName: UserName) = userRepository.get(userName)
}