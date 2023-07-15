package com.bbopgi.nanoogoogae.global.exception

import com.bbopgi.nanoogoogae.global.common.CommonApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(NanoogoogaeException::class)
    fun handleNanoogoogaeException(e: NanoogoogaeException): CommonApiResponse<*> {
        return CommonApiResponse<Unit>().error(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): CommonApiResponse<*> {
        return CommonApiResponse<Unit>()
            .error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.message ?: "알 수 없는 오류가 발생했습니다.")
    }
}