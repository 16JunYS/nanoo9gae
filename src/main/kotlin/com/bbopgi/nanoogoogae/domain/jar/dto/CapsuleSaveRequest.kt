package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 작성 DTO")
data class CapsuleSaveRequest(
    @field:Schema(description = "작성 유저 닉네임", example = "anonymous_user", required = false)
    var authorNickname: String,
    var content: String,
    var color: String,
    @field:Schema(description = "편지 공개/비공개 유무", example = "true (공개인 경우)")
    var isPublic: Boolean,
)
