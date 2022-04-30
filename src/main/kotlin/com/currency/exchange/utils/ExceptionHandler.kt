package com.currency.exchange.utils

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun generalExceptionHandler(exception: Exception): ResponseEntity<ApiError> {
        val error = ApiError(_message = exception.message)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: Exception): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.NOT_FOUND, _message = exception.message)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(BadFormatException::class)
    fun handleBadFormatException(exception: Exception): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.BAD_REQUEST, _message = exception.message)
        return ResponseEntity(error, error.status)
    }
}