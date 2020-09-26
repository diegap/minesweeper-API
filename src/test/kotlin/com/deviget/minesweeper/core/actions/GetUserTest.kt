package com.deviget.minesweeper.core.actions

import com.deviget.minesweeper.core.actions.user.GetUser
import com.deviget.minesweeper.core.domain.entities.User
import com.deviget.minesweeper.core.domain.entities.UserName
import com.deviget.minesweeper.core.domain.repositories.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.Verify
import org.amshove.kluent.When
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test

class GetUserTest {

	private lateinit var userRepository: UserRepository
	private var user: User? = null
	private lateinit var getUser: GetUser

	@Test
	fun `retrieve existing user`() {
		givenUserRepository()
		givenGetUserAction()
		whenActionIsInvoked()
		thenUserIsLookedUp()
		thenUserIsRetrieved()
	}

	@Test
	fun `non existing user`() {
		givenUserNotFoundInRepository()
		givenGetUserAction()
		whenActionIsInvoked()
		thenUserIsLookedUp()
		thenUserIsNotFound()
	}

	private fun givenUserRepository() {
		userRepository = mock()
		When calling userRepository.get(UserName("user1")) itReturns User(UserName("user1"))
	}

	private fun givenUserNotFoundInRepository() {
		userRepository = mock()
		When calling userRepository.get(any()) itReturns null
	}

	private fun givenGetUserAction() {
		getUser = GetUser(userRepository)
	}

	private fun whenActionIsInvoked() {
		user = getUser(UserName("user1"))
	}

	private fun thenUserIsRetrieved() {
		user `should not be` null
	}

	private fun thenUserIsNotFound() {
		user `should be` null
	}

	private fun thenUserIsLookedUp() {
		Verify on userRepository that userRepository.get(any()) was called
	}

}