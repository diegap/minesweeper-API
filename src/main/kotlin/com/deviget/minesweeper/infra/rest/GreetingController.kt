package com.deviget.minesweeper.infra.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

	@GetMapping("/greeting")
	fun greet() = "hello!"
}