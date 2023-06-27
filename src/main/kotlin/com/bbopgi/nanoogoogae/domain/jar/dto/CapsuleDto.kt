package com.bbopgi.nanoogoogae.domain.jar.dto

import java.util.*

data class CapsuleDto(
    var capsuleId: Int,
    var authorNickname: String,
    var createdAt: Date,
    var isOpened: Boolean,
    var emojiReply: String,
    var isPrivate: Boolean,
    var isRead: Boolean,
    var type: String,
)
