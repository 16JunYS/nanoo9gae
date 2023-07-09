package com.bbopgi.nanoogoogae.global.exception

import java.lang.RuntimeException

class NanoogoogaeException(
    private val errorMessage: String = "알 수 없는 오류가 발생했습니다."
): RuntimeException() {

    override val message = errorMessage
}