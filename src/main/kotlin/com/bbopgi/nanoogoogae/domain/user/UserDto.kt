package com.bbopgi.nanoogoogae.domain.user

import io.swagger.v3.oas.annotations.media.Schema

data class UserDto(
    var userId: String,

    @field:Schema(
        description = "편지에 보여지는 유저 닉네임",
        example="nickname11",
        type = "string",
    )
    var nickname: String,

    @field:Schema(description = "유저 비밀번호", example="abcde12", required = true)
    var password: String,

    @field:Schema(example="01012341234", required = true)
    var phoneNumber: String,

    @field:Schema(example="5", required = true)
    var coin: Int,
)
