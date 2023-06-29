package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.global.entity.User
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

    @field:Schema(description = "공백인 경우 default 5", example="5", required = false)
    var coin: Int = 5,
)

fun UserDto.toEntity() = User(
    userId = this.userId,
    nickname = this.nickname,
    password = this.password,
    phoneNumber = this.phoneNumber,
    coin = this.coin,
)

data class UserCreatePayload(
    @field:Schema(
        description = "유저의 뽑기통 url path가 될 뽑기통 16자리의 ID",
        example="abcde12345abcde1"
    )
    var jarId: String,
)
