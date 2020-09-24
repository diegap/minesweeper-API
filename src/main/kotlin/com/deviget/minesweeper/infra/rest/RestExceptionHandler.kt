package com.deviget.minesweeper.infra.rest

import com.deviget.minesweeper.core.domain.exceptions.CellCannotBeRevealedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ApiError(val message: String)

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

	@ExceptionHandler(
			IllegalArgumentException::class,
			IllegalStateException::class,
			CellCannotBeRevealedException::class
	)
	fun handle(exception: RuntimeException, request: WebRequest) =
			ResponseEntity(ApiError(exception.message ?: "Error"), HttpStatus.CONFLICT)
}