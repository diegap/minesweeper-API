package com.deviget.minesweeper.infra.rest.controllers

import com.deviget.minesweeper.core.domain.exceptions.CellCannotBeRevealedException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ApiError(val message: String)

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

	@ExceptionHandler(IllegalArgumentException::class)
	fun handle(exception: Exception) =
			ResponseEntity(ApiError("Invalid argument provided"), BAD_REQUEST)

	@ExceptionHandler
	fun handle(exception: CellCannotBeRevealedException) =
			ResponseEntity(ApiError("Cell cannot be revealed"), CONFLICT)

	@ExceptionHandler(IllegalStateException::class)
	fun handle(exception: RuntimeException) =
			ResponseEntity(ApiError("Invalid action provided for current state"), CONFLICT)

	@ExceptionHandler(Exception::class)
	fun handle(exception: Exception, request: WebRequest) =
			ResponseEntity(ApiError("Cannot process request"), INTERNAL_SERVER_ERROR)

}