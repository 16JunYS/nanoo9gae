package com.bbopgi.nanoogoogae.domain.jar.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 작성 DTO")
data class CapsuleSaveRequest(
    @field:Schema(description = "작성 유저 닉네임", example = "anonymous_user")
    var authorNickname: String,
    var content: String,
    var color: String,
    @field:Schema(description = "편지 공개/비공개 유무", example = "true (공개인 경우)")
    var isPublic: Boolean,
    // [TODO] 사용자 인증 구현 이후 삭제
    @field:Schema(description = "작성 유저 아이디, 비회원인 경우 생략가능", example = "user_id", required = false)
    var userId: String?=null,
)
