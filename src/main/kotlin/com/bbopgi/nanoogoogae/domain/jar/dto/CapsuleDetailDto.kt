package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Date

@Schema(description = "캡슐 상세 정보")
data class CapsuleDetailDto(
    @field:Schema(description = "작성 유저 닉네임", example = "anonymous_user")
    var authorNickname: String,
    var text: String,
    var createdAt: Date,
    @field:Schema(description = "이모지 반응")
    var emojiReply: String,
    @field:Schema(description = "편지(뽑기) 담긴 뽑기통 jarID")
    var jarId: String,
    @field:Schema(description = "편지 공개/비공개 유무", example = "true (공개인 경우)")
    var isPublic: Boolean,
    @field:Schema(description = "편지 읽음 유무", example = "true")
    var isRead: Boolean,
    @field:Schema(description = "일반 편지 또는 답장인지 구분하는 필드")
    var type: String,
)
