package com.bbopgi.nanoogoogae.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(NanoogoogaeException::class)
    fun handleNanoogoogaeException(e: NanoogoogaeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(e.message ?: "알 수 없는 오류가 발생했습니다."))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError().body(
            ErrorResponse(e.message ?: "알 수 없는 오류가 발생했습니다."))
    }
}

class ErrorResponse(
    val message: String,
)