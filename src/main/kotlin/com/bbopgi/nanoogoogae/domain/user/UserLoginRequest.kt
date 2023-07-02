package com.bbopgi.nanoogoogae.domain.user

data class UserLoginRequest(
    val id: String,
    val password: String,
)

data class UserLoginResponse(
    val token: String,
    val jarId: String,
)
