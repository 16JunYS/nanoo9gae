package com.bbopgi.nanoogoogae.global.entity

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.Date

@Document(collection = "capsule")
data class Capsule(
    @Id
    var capsuleId: Long,

    @Field("author_nickname")
    var authorNickname: String,

    @Field("author_id")
    var authorId: String,

    var text: String,

    @Field("created_at")
    var createdAt: Date,

    @Field("emoji_reply")
    var emojiReply: String,

    @Field("jar_id")
    var jarId: String,

    @Field("is_public")
    var isPrivate: Boolean,

    @Field("is_read")
    var isRead: Boolean = false,

    @Field("reply_from")
    var replyFrom: String? = null,

    var color: String,
)

fun Capsule.toDto() = CapsuleDto(
    capsuleId = this.capsuleId,
    authorNickname = this.authorNickname,
    createdAt = this.createdAt,
    emojiReply = this.emojiReply,
    isPublic = this.isPrivate,
    isRead = this.isRead,
    type = if (this.replyFrom == null) "normal" else "reply",
    color = this.color,
)