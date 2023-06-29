package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema

data class JarDto(
    @field:Schema(description = "뽑기통 주인 유저 닉네임", example = "anonymous_user")
    val userNickname: String,

    val capsules: List<CapsuleDto>,

    val coin: Int = 5,
)