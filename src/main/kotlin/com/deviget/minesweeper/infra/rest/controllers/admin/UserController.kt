package com.deviget.minesweeper.infra.rest.controllers.admin

import com.deviget.minesweeper.core.actions.user.CreateUser
import com.deviget.minesweeper.infra.rest.representations.UserRepresentation
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val createUser: CreateUser) {

	@PostMapping("/admin/users")
	fun createUser(@RequestBody userRepresentation: UserRepresentation) = run {
		createUser(userRepresentation.toDomain())
		CREATED
	}
}