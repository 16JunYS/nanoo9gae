package com.bbopgi.nanoogoogae.global.entity

import com.bbopgi.nanoogoogae.domain.user.dto.UserDto
import com.bbopgi.nanoogoogae.domain.user.dto.UserPublicDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "user")
data class User(
    @Field("user_id")
    @Id
    @field:Schema(description = "유저 아이디", example="user_id1", required = true)
    var userId: String,

    @field:Schema(
        description = "편지에 보여지는 유저 닉네임",
        example="nickname11",
        type = "string",
    )
    var nickname: String,

    @field:Schema(description = "유저 비밀번호", example="abcde12", required = true)
    var password: String,
    @Field("phone_number")
    @field:Schema(example="01012341234", required = true)
    var phoneNumber: String,
    @field:Schema(example="5", required = true)
    var coin: Int,

    @Field("jar_id")
    var jarId: String,

    @Field("last_login_at")
    var lastLoginAt: LocalDateTime? = null,
)

fun User.toDto() = UserDto(
    userId = this.userId,
    nickname = this.nickname,
    password = this.password,
    phoneNumber = this.phoneNumber,
    coin = this.coin,
    jarId = this.jarId,
    lastLoginAt = this.lastLoginAt,
)

fun User.toPublicDto() = UserPublicDto(
    userId = this.userId,
    nickname = this.nickname,
    phoneNumber = this.phoneNumber,
    coin = this.coin,
    jarId = this.jarId,
    lastLoginAt = this.lastLoginAt,
)