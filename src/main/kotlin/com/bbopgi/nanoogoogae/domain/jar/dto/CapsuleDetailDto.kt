package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.Date

@Schema(description = "캡슐 상세 정보")
data class CapsuleDetailDto(
    @field:Schema(description = "작성 유저 닉네임", example = "anonymous_user")
    var authorNickname: String,

    @field:Schema(description = "작성 유저 ID. empty string인 경우 비회원", example = "")
    var authorId: String? = "",

    var content: String,

    var createdAt: LocalDateTime,

    @field:Schema(description = "이모지 반응")
    var emojiReply: String?,

    @field:Schema(description = "편지(뽑기) 담긴 뽑기통 jarID")
    var jarId: String,

    @field:Schema(description = "편지 공개/비공개 유무", example = "true (공개인 경우)")
    var isPublic: Boolean,

    @field:Schema(description = "편지 읽음 유무", example = "true")
    var isRead: Boolean = false,

    @field:Schema(description = "일반 편지 또는 답장인지 구분하는 필드")
    var type: String,

    @field:Schema(description = "답장을 보낸 경우 true")
    var isReplied: Boolean = false,

    var color: String,
)
