package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.actions.user.CreateUser
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.Verify
import org.amshove.kluent.called
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test

class CreateUserTest {

	private lateinit var createUser: CreateUser
	private lateinit var userRepository: UserRepository

	@Test
	fun `create user`() {
		givenCreateUserAction()

		whenActionIsInvoked(User(UserName("user1")))

		thenUserIsCreated()

	}

	private fun givenCreateUserAction() {
		userRepository = mock()
		createUser = CreateUser(userRepository)
	}

	private fun whenActionIsInvoked(user: User) {
		createUser(user)
	}

	private fun thenUserIsCreated() {
		Verify on userRepository that userRepository.save(any()) was called
	}
}