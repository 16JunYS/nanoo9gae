package com.bbopgi.nanoogoogae.domain.user.dto

import java.time.LocalDateTime

data class UserLoginRequest(
    val id: String,
    val password: String,
)

data class UserLoginResponse(
    val token: String,
    val jarId: String,
    val lastLoginAt: LocalDateTime? = null,
)
