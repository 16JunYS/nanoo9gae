package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지에 이모지를 추가하는 DTO")
data class CapsuleEmojiRequest(
    var emoji: Int,
    var dumpField: String?="",
)
