package com.bbopgi.nanoogoogae.global.common

import org.springframework.http.HttpStatus

data class CommonApiResponse<T>(
    val status: Int? = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    val data: T? = null,
    val message: String? = null,
) {
    fun success(data: T? = null): CommonApiResponse<T> {
        return CommonApiResponse(HttpStatus.OK.value(), data, null)
    }

    fun created(data: T? = null): CommonApiResponse<T> {
        return CommonApiResponse(HttpStatus.CREATED.value(), data, null)
    }

    fun error(message: String? = null): CommonApiResponse<T> {
        return CommonApiResponse(HttpStatus.BAD_REQUEST.value(), null, message)
    }

    fun error(status: Int, message: String? = null): CommonApiResponse<T> {
        return CommonApiResponse(status, null, message)
    }
}
