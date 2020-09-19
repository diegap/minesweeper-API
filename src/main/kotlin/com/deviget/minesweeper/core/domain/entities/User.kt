package com.deviget.minesweeper.core.domain.entities

class User(val userName: UserName)

data class UserName(val value: String) {
	init {
		require(value.isNotEmpty())
	}
}
