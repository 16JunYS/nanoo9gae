package com.bbopgi.nanoogoogae.domain.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class UserPublicDto(
    var userId: String,

    @field:Schema(
        description = "편지에 보여지는 유저 닉네임",
        example="nickname11",
        type = "string",
    )
    var nickname: String,

    @field:Schema(example="01012341234", required = true)
    var phoneNumber: String,

    @field:Schema(description = "공백인 경우 default 5", example="5", required = false)
    var coin: Int = 5,

    var jarId: String,

    var lastLoginAt: LocalDateTime? = null,
)
