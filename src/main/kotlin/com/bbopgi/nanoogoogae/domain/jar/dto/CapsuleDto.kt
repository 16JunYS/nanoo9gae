package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class CapsuleDto(
    var capsuleId: Long,

    @field:Schema(description = "편지 작성 유저 닉네임", example = "anonymous_user")
    var authorNickname: String,

    var createdAt: Date,

    var emojiReply: String?,

    @field:Schema(description = "편지 공개/비공개 유무", example = "true (공개인 경우)")
    var isPublic: Boolean,

    @field:Schema(description = "편지 읽음 유무", example = "true")
    var isRead: Boolean = false,

    @field:Schema(description = "일반 편지 또는 답장인지 구분하는 필드")
    var type: String,

    var color: String,
)
